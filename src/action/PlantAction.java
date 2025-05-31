package action;

import main.GamePanel;
import model.ItemType;
import model.CropData;
import entity.Player;
import model.Inventory;
import model.Item; 

public class PlantAction implements Action {
    private GamePanel gp;
    private int col;
    private int row;
    private String caseType;

    public PlantAction(GamePanel gp, String caseType) {
        this.gp = gp;
        this.col = gp.ui.plantingCol;
        this.row = gp.ui.plantingRow;
        this.caseType = caseType;
        execute();
    }

    @Override
    public void execute() {
        int tileNum = gp.farmMap.mapTileNum[col][row];
        Player player = gp.player;
        Inventory inv = player.getInventory();

        // Check energy
        if (player.getEnergy() < 5) {
            gp.ui.currentDialogue = "You don't have enough energy!";
            gp.gameState = gp.dialogueState;
            return;
        }

        switch (caseType) {
        case "Tiling":
            if (!inv.hasItem("Hoe")) {
                gp.ui.currentDialogue = "You need a Hoe!";
                gp.gameState = gp.dialogueState;
                return;
            }
            if (tileNum == 0) {
                gp.farmMap.mapTileNum[col][row] = 2;
                player.setEnergy(-5);
                gp.ui.currentDialogue = "Soil tilled.";
            } else {
                gp.ui.currentDialogue = "Can't till this tile.";
            }
            break;

        case "Recover":
            if (!inv.hasItem("Pickaxe")) {
                gp.ui.currentDialogue = "You need a Pickaxe!";
                gp.gameState = gp.dialogueState;
                return;
            }
            if (tileNum == 2 && gp.farmMap.cropInfo[col][row] == null) {
                gp.farmMap.mapTileNum[col][row] = 0;
                player.setEnergy(-5);
                gp.ui.currentDialogue = "Soil reverted.";
            } else {
                gp.ui.currentDialogue = "Can't recover this tile.";
            }
            break;

        case "Plant":
            if (tileNum == 2 && gp.farmMap.cropInfo[col][row] == null) {
                if (inv.getItemsByType(ItemType.SEED).isEmpty()) {
                    gp.ui.currentDialogue = "You have no seeds!";
                    gp.gameState = gp.dialogueState;
                    return;
                }
                gp.ui.plantingCol = col;
                gp.ui.plantingRow = row;
                gp.ui.filteredItems.clear();
                gp.ui.filteredItems.addAll(inv.getItemsByType(ItemType.SEED));
                gp.ui.filteredInventorySelectionIndex = 0;
                gp.gameState = gp.plantInventoryState;
                return; // tidak kurangi energi di sini
            } else {
                gp.ui.currentDialogue = "Can't plant here.";
            }
            break;

        case "Water":
            if (!inv.hasItem("Watering Can")) {
                gp.ui.currentDialogue = "You need a Watering Can!";
                gp.gameState = gp.dialogueState;
                return;
            }
            if (tileNum == 5 && gp.farmMap.cropInfo[col][row] != null && gp.farmMap.cropInfo[col][row].wateredToday == false) {
                gp.farmMap.cropInfo[col][row].wateredToday = true;
                player.setEnergy(-5);
                gp.ui.currentDialogue = "Plant watered.";
            } else if ( tileNum == 5 && gp.farmMap.cropInfo[col][row] != null && gp.farmMap.cropInfo[col][row].wateredToday == true) {
                gp.ui.currentDialogue = "Already watered today.";
            }
            else {
                gp.ui.currentDialogue = "Nothing to water here.";
            }
            break;

        case "Harvest":
            if (tileNum == 5 && gp.farmMap.cropInfo[col][row] != null &&
                isReadyToHarvest(gp.farmMap.cropInfo[col][row])) {
                String crop = gp.farmMap.cropInfo[col][row].seedName.replace(" Seeds", "");
                player.addItem(crop, 1, ItemType.CROPS);
                gp.farmMap.mapTileNum[col][row] = 0;
                gp.farmMap.cropInfo[col][row] = null;
                player.setEnergy(-5);
                gp.ui.currentDialogue = "You harvested a " + crop + "!";
            } else {
                gp.ui.currentDialogue = "Nothing to harvest.";
            }
            break;
        }
        gp.gameState = gp.dialogueState;
    }

    public void executePlantLogic(GamePanel gp) {
        this.gp = gp;
        int col = gp.ui.plantingCol;
        int row = gp.ui.plantingRow;
        Item selectedFood = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
        gp.farmMap.mapTileNum[col][row] = 5;
        gp.farmMap.cropInfo[col][row] = new CropData();
        gp.farmMap.cropInfo[col][row].seedName = selectedFood.getName();
        gp.farmMap.cropInfo[col][row].daysPlanted = 0;
        gp.farmMap.cropInfo[col][row].wateredToday = gp.farmMap.isRainyDay();
        gp.ui.currentDialogue = "Seed planted: " + selectedFood.getName();;
        gp.gameState = gp.dialogueState;
        gp.player.inventory.removeItem(selectedFood.getName(), 1);
    }

    public static int getDaysToHarvest(String seedName) {
    return switch (seedName.toLowerCase()) {
        case "parsnip seeds", "wheat seeds", "hot pepper seeds" -> 1;
        case "cauliflower seeds" -> 5;
        case "potato seeds", "tomato seeds", "grape seeds" -> 3;
        case "melon seeds" -> 4;
        case "blueberry seeds" -> 7;
        case "pumpkin seeds" -> 7;
        case "cranberry seeds" -> 2;
        default -> 3;
    };
}

    public static boolean isReadyToHarvest(CropData data) {
        return data.daysPlanted >= getDaysToHarvest(data.seedName);
    }
}