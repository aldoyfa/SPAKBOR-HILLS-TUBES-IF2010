package action;

import entity.Player;
import main.GamePanel;

public class RecoverLandAction implements Action {
    private int x, y;
    private GamePanel gp;

    public RecoverLandAction(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            gp.addMessage("Energi tidak cukup untuk recovery tanah.");
            return;
        }

        if (!player.getInventory().hasTool("Pickaxe")) {
            gp.addMessage("Kamu butuh Pickaxe untuk melakukan recovery.");
            return;
        }

        if (gp.farmMap[x][y].isSoil()) {
            gp.farmMap[x][y].resetToLand();
            player.reduceEnergy(5);
            player.time.tick();
            gp.addMessage("Tile berhasil dikembalikan ke bentuk land di (" + x + "," + y + ")");
        } else {
            gp.addMessage("Tile ini bukan tanah (soil).");
        }
    }
}
