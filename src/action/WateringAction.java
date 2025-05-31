package action;

import entity.Player;
import main.GamePanel;

public class WateringAction implements Action {
    private int x, y;
    private GamePanel gp;

    public WateringAction(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk menyiram.");
            return;
        }

        if (gp.farmMap[x][y].hasPlant()) {
            gp.farmMap[x][y].water();
            player.reduceEnergy(5);
            player.time.tick();
            System.out.println("Tanaman disiram di: " + x + ", " + y);
        } else {
            System.out.println("Tidak ada tanaman untuk disiram.");
        }
    }
}
