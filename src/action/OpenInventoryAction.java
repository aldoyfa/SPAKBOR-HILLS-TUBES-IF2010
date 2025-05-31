package action;

import main.GamePanel;

public class OpenInventoryAction implements Action {
    private GamePanel gp;

    public OpenInventoryAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Reset inventory selection index
        gp.ui.inventorySelectionIndex = 0;
        
        // Masuk ke inventory state
        gp.gameState = gp.inventoryState;
    }
}
