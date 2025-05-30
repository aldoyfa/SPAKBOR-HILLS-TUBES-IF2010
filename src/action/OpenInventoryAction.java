package action;

import main.GamePanel;

public class OpenInventoryAction implements Action {
    private GamePanel gp;

    public OpenInventoryAction(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void execute() {
        gp.player.inventory.printInventory();
    }
}