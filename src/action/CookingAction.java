package action;

import main.GamePanel;
import model.Item;
import model.ItemType;
import model.Recipe;

public class CookingAction {

    public static void execute(GamePanel gp, Recipe selectedRecipe) {
        if (!isNearStove(gp)) {
            gp.ui.currentDialogue = "You need to be next to a stove to cook!";
            gp.gameState = gp.dialogueState;
            return;
        }

        if (gp.player.getEnergy() < 10) {
            gp.ui.currentDialogue = "Not enough energy to start cooking!";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Tentukan bahan bakar valid berdasarkan fuelRequired
        Item fuel = chooseFuel(gp, selectedRecipe.getFuelRequired());
        if (fuel == null) {
            gp.ui.currentDialogue = "Missing fuel to cook " + selectedRecipe.getName() + ".";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Cek bahan-bahan masakan
        if (!hasIngredients(gp, selectedRecipe)) {
            gp.ui.currentDialogue = "Missing ingredients to cook " + selectedRecipe.getName() + ".";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Eksekusi memasak
        gp.player.setEnergy(-10);
        consumeIngredients(gp, selectedRecipe);
        gp.player.getInventory().removeItem(fuel.getName(), fuel.getQuantity());

        // Tambahkan hasil masakan
        gp.farmMap.time.addMinutes(60); // pasif 1 jam
        Item result = selectedRecipe.getResult();
        gp.player.addItem(result.getName(), result.getQuantity(), result.getType());

        gp.ui.currentDialogue = "You successfully cooked " + result.getName() + "!";
        gp.gameState = gp.dialogueState;
    }

    // Cek apakah player berada di samping Stove
    private static boolean isNearStove(GamePanel gp) {
        int px = gp.player.worldX / gp.tileSize;
        int py = gp.player.worldY / gp.tileSize;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && gp.obj[i].name.equals("Stove")) {
                int sx = gp.obj[i].worldX / gp.tileSize;
                int sy = gp.obj[i].worldY / gp.tileSize;
                if (Math.abs(px - sx) + Math.abs(py - sy) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // Cek apakah semua bahan tersedia
    private static boolean hasIngredients(GamePanel gp, Recipe recipe) {
        for (Item required : recipe.getIngredients()) {
            if (gp.player.getInventory().getItemQuantity(required.getName()) < required.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    // Kurangi bahan dari inventory
    private static void consumeIngredients(GamePanel gp, Recipe recipe) {
        for (Item required : recipe.getIngredients()) {
            gp.player.getInventory().removeItem(required.getName(), required.getQuantity());
        }
    }

    // Cek dan pilih fuel yang tersedia sesuai jumlah fuelRequired
    private static Item chooseFuel(GamePanel gp, int fuelRequired) {
        // Prioritaskan coal jika butuh 2 fuel
        if (fuelRequired == 2) {
            if (gp.player.getInventory().hasItem("Coal", 1)) {
                return new Item("Coal", 1, ItemType.MISC);
            } else if (gp.player.getInventory().hasItem("Firewood", 2)) {
                return new Item("Firewood", 2, ItemType.MISC);
            }
        }

        // Kalau cuma butuh 1
        if (fuelRequired == 1) {
            if (gp.player.getInventory().hasItem("Firewood", 1)) {
                return new Item("Firewood", 1, ItemType.MISC);
            } else if (gp.player.getInventory().hasItem("Coal", 1)) {
                return new Item("Coal", 1, ItemType.MISC); // overkill, tapi valid
            }
        }

        return null;
    }
}