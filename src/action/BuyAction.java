package action;

import main.GamePanel;
import model.*;

public class BuyAction implements Action {
    private GamePanel gp;

    public BuyAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // This is handled by shop navigation now
    }

    // Static method untuk execute buy logic
    public static void executeBuyLogic(GamePanel gp, String itemName) {
        int buyPrice = ShopDatabase.getBuyPrice(itemName);

        if (buyPrice == 0) {
            gp.ui.currentDialogue = "Sorry, that item is not available for purchase.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Check if player has enough gold
        if (gp.player.getGold() < buyPrice) {
            gp.ui.currentDialogue = "You don't have enough gold!\nYou need " + buyPrice + "g but only have " + gp.player.getGold() + "g.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Special check untuk Proposal Ring (hanya bisa dibeli sekali)
        if (itemName.equals("Proposal Ring") && gp.player.hasItem("Proposal Ring")) {
            gp.ui.currentDialogue = "You already have a Proposal Ring!\nYou can only buy this once.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Execute purchase
        gp.player.setGold(-buyPrice); // Kurangi gold

        // Add item to inventory using database
        gp.player.getInventory().addItemByName(itemName, 1);

        gp.ui.currentDialogue = "You bought " + itemName + " for " + buyPrice + "g!\nThank you for your purchase.";
        gp.gameState = gp.dialogueState;

        System.out.println("SHOP: Bought " + itemName + " for " + buyPrice + "g");
    }
}