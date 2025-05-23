package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;
import model.Tile;

public class TillingAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 5); // 5 menit

    @Override
    public void execute(Player player, Farm farm, String args) {
        // args = "x,y"
        String[] parts = args.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        Tile tile = farm.getFarmMap().getTile(x, y);

        if (tile.getType() != TileType.TILLABLE) {
            System.out.println("Tile tidak bisa dibajak.");
            return;
        }

        if (player.getEnergy() < energyCost) {
            System.out.println("Energi tidak cukup.");
            return;
        }

        tile.setType(TileType.TILLED);
        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);
        System.out.println("Tanah di (" + x + "," + y + ") telah dibajak!");
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
