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
        // NEW: Get sellable categories dari player inventory
        List<ItemType> sellableCategories = ShopDatabase.getSellableCategories(gp);
        
        if (sellableCategories.isEmpty()) {
            gp.ui.currentDialogue = "You don't have any items that I can buy!";
            gp.gameState = gp.dialogueState;
            return;
        }
        
        // Setup sell category selection
        gp.ui.sellCategories.clear();
        gp.ui.sellCategories.addAll(sellableCategories);
        gp.ui.sellCategoryIndex = 0;
        gp.gameState = gp.sellCategoryState; // FIX: Use correct state
    }
    
    // FIX: Method signature untuk string parameter
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
        gp.player.setGold(sellPrice); // Add gold
        gp.player.removeItem(itemName, 1); // Remove 1 quantity
        
        gp.ui.currentDialogue = "You sold " + itemName + " for " + sellPrice + "g!\nPleasure doing business with you.";
        gp.gameState = gp.dialogueState;
        
        System.out.println("SHOP: Sold " + itemName + " for " + sellPrice + "g");
    }
    
    // LEGACY: Method untuk old sell inventory flow (backup)
    public static void executeSellLogic(GamePanel gp) {
        if (gp.ui.filteredItems == null || gp.ui.filteredItems.isEmpty()) {
            return;
        }
        
        Item selectedItem = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
        executeSellLogic(gp, selectedItem.getBaseName());
        
        // Clear filtered items
        gp.ui.filteredItems.clear();
    }
}