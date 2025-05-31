package objects;

import main.GamePanel;

public class ShippingBin extends Object {
    
    public ShippingBin(GamePanel gp) {
        this.gp = gp;
        name = "Shipping Bin";
        width = 64 * 3; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
    }

    public void interact() {
        // IMPORTANT: Clear any previous NPC interaction state
        gp.ui.currentNPC = null;
        gp.ui.isEmilyShop = false;

        // Set dialogue untuk shipping bin
        gp.ui.currentDialogue = "Welcome to the Shipping Bin!\nYou can sell your items here for gold.";

        // Set flag untuk shipping bin mode
        gp.ui.isShippingBinMode = true;

        // Go to dialogue state first, then transition to sell
        gp.gameState = gp.dialogueState;
    }
}