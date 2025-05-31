package action;

import objects.NPC;
import main.GamePanel;

public class Marry implements Action {
    private GamePanel gp;
    private NPC npc;

    public Marry(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
    }
}