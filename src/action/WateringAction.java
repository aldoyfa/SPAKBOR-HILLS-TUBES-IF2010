package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;
import model.Tile;

public class WateringAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 5);

    @Override
    public void execute(Player player, Farm farm, String args) {
        String[] parts = args.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        Tile tile = farm.getFarmMap().getTile(x, y);

        if (tile.getType() != TileType.PLANTED || tile.isWatered()) {
            System.out.println("Tile tidak bisa disiram.");
            return;
        }

        tile.setWatered(true);
        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);

        System.out.println("Menyiram tanaman di tile (" + x + "," + y + ")");
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
