package action;

import main.GamePanel;

public class ShowTimeAction implements Action {
    private GamePanel gp;

    public ShowTimeAction(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void execute() {
        System.out.println(gp.player.time.getCurrentTime());
    }
}