package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;
import model.Tile;

public class PlantingAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 5);

    @Override
    public void execute(Player player, Farm farm, String args) {
        // Format args = "x,y,SeedName"
        String[] parts = args.split(",");
        if (parts.length < 3) {
            System.out.println("Input tidak valid. Gunakan format: x,y,nama_seed");
            return;
        }

        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        String seedName = parts[2].trim();

        Tile tile = farm.getFarmMap().getTile(x, y);

        if (tile.getType() != TileType.TILLED || tile.getCrop() != null) {
            System.out.println("Tile tidak bisa ditanami.");
            return;
        }

        Inventory inventory = player.getInventory();
        Item seedItem = null;
        for (Item item : inventory.getAllItems().keySet()) {
            if (item instanceof Seeds && item.getName().equalsIgnoreCase(seedName)) {
                seedItem = item;
                break;
            }
        }

        if (seedItem == null) {
            System.out.println("Benih tidak ditemukan di inventory.");
            return;
        }

        Seeds seed = (Seeds) seedItem;
        if (seed.getSeason() != farm.getSeason()) {
            System.out.println("Benih hanya bisa ditanam di musim " + seed.getSeason());
            return;
        }

        // Eksekusi tanam
        tile.setType(TileType.PLANTED);
        tile.setCrop(new Crops(seed.getName())); // Asumsi 1:1 antara seed & crop

        inventory.removeItem(seed, 1);
        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);

        System.out.println("Menanam " + seed.getName() + " di tile (" + x + "," + y + ")");
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
