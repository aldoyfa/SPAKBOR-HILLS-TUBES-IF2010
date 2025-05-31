package action;

import entity.Player;
import main.GamePanel;
import model.Seed;

public class PlantingAction implements Action {
    private int x, y;
    private Seed seed;
    private GamePanel gp;

    public PlantingAction(GamePanel gp, int x, int y, Seed seed) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.seed = seed;
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk menanam.");
            return;
        }

        if (!player.getInventory().contains(seed)) {
            System.out.println("Kamu tidak memiliki seed ini.");
            return;
        }

        if (gp.farmMap[x][y].isTilled() && gp.farmMap[x][y].isEmpty()) {
            gp.farmMap[x][y].plant(seed);
            player.getInventory().remove(seed);
            player.reduceEnergy(5);
            player.time.tick();
            System.out.println("Seed ditanam di: " + x + ", " + y);
        } else {
            System.out.println("Tile ini tidak bisa ditanami.");
        }
    }
}
