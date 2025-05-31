package action;

import objects.NPC;
import main.GamePanel;

public class Chatting implements Action {
    private GamePanel gp;
    private NPC npc;

    public Chatting(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
        if (gp.player.getEnergy() >= 10) {
            // ENHANCED: Set currentNPC untuk shop integration
            gp.ui.currentNPC = npc;
            
            gp.gameState = gp.dialogueState;
            npc.speak(); // Emily's speak() method will set isEmilyShop = true
            gp.player.setEnergy(-10);
            npc.setHeartPoints(10);
        }
    }
}