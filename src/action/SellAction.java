package action;

import main.GamePanel;
import model.*;
import java.util.List;

public class SellAction implements Action {
    private GamePanel gp;

    public SellAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Get sellable categories dari player inventory
        List<ItemType> sellableCategories = ShopDatabase.getSellableCategories(gp);

        if (sellableCategories.isEmpty()) {
            if (gp.ui.isShippingBinMode) {
                gp.ui.currentDialogue = "You don't have any items to ship!";
            } else {
                gp.ui.currentDialogue = "You don't have any items that I can buy!";
            }
            gp.gameState = gp.dialogueState;
            return;
        }

        // Setup sell category selection
        gp.ui.sellCategories.clear();
        gp.ui.sellCategories.addAll(sellableCategories);
        gp.ui.sellCategoryIndex = 0;
        gp.gameState = gp.sellCategoryState;
    }

    // EXISTING: Emily shop sell logic
    public static void executeSellLogic(GamePanel gp, String itemName) {
        int sellPrice = ShopDatabase.getSellPrice(itemName);

        if (sellPrice == 0) {
            gp.ui.currentDialogue = "Sorry, I can't buy that item.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Check if player has the item
        if (!gp.player.hasItem(itemName)) {
            gp.ui.currentDialogue = "You don't have " + itemName + " to sell!";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Execute sale
        gp.player.setGold(sellPrice);
        gp.player.removeItem(itemName, 1);

        gp.ui.currentDialogue = "You sold " + itemName + " for " + sellPrice + "g!\nPleasure doing business with you.";
        gp.gameState = gp.dialogueState;

        System.out.println("SHOP: Sold " + itemName + " for " + sellPrice + "g");
    }

    // NEW: Shipping Bin logic dengan different messaging
    public static void executeShipLogic(GamePanel gp, String itemName) {
        int shipPrice = ShopDatabase.getSellPrice(itemName);

        if (shipPrice == 0) {
            gp.ui.currentDialogue = "This item cannot be shipped.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Check if player has the item
        if (!gp.player.hasItem(itemName)) {
            gp.ui.currentDialogue = "You don't have " + itemName + " to ship!";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Execute shipping
        gp.player.setGold(shipPrice);
        gp.player.removeItem(itemName, 1);

        gp.ui.currentDialogue = "You shipped " + itemName + " for " + shipPrice + "g!\nItems successfully sent to market.";

        // ENHANCED: Ensure currentNPC is null untuk shipping dialogue
        gp.ui.currentNPC = null;

        gp.gameState = gp.dialogueState;

        System.out.println("SHIPPING: Shipped " + itemName + " for " + shipPrice + "g");
    }

    // LEGACY: Method untuk old sell inventory flow (backup)
    public static void executeSellLogic(GamePanel gp) {
        if (gp.ui.filteredItems == null || gp.ui.filteredItems.isEmpty()) {
            return;
        }

        Item selectedItem = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
        executeSellLogic(gp, selectedItem.getBaseName());

        gp.ui.filteredItems.clear();
    }
}