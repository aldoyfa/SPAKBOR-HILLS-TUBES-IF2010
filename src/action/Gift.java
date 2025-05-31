package action;

import objects.NPC;
import main.GamePanel;

public class Gift implements Action {
    private GamePanel gp;
    private NPC npc;

    public Gift(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
        // Set target NPC dan masuk ke inventory selection state
        gp.ui.targetNPC = npc;
        gp.ui.inventorySelectionIndex = 0; // Reset posisi selection
        gp.gameState = gp.inventorySelectionState;
    }
}