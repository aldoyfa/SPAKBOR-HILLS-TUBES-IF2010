package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;
import model.Tile;

public class HarvestingAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 5);

    @Override
    public void execute(Player player, Farm farm, String args) {
        String[] parts = args.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        Tile tile = farm.getFarmMap().getTile(x, y);

        if (tile.getType() != TileType.PLANTED || tile.getCrop() == null) {
            System.out.println("Tidak ada tanaman untuk dipanen.");
            return;
        }

        Crops crop = tile.getCrop();
        player.getInventory().addItem(crop, crop.getHarvestAmount());

        tile.setCrop(null);
        tile.setType(TileType.TILLED);
        tile.setWatered(false);

        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);

        System.out.println("Memanen " + crop.getName() + " di tile (" + x + "," + y + ")");
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
