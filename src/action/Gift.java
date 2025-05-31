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
    }
}