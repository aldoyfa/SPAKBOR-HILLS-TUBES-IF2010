package action;

import entity.Player;
import main.GamePanel;

public class TillingAction implements Action {
    private int x, y; // posisi tile yang ingin dibajak
    private GamePanel gp;

    public TillingAction(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            gp.addMessage("Energi tidak cukup untuk membajak.");
            return;
        }

        if (gp.farmMap[x][y].canBeTilled()) {
            gp.farmMap[x][y].till();
            player.reduceEnergy(5);
            player.time.tick();
            gp.addMessage("Tanah dibajak di: " + x + ", " + y);
        } else {
            gp.addMessage("Tile ini sudah dibajak.");
        }
    }
}
