package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;
import model.Tile;

public class RecoverLandAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 5);

    @Override
    public void execute(Player player, Farm farm, String args) {
        String[] parts = args.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());

        Tile tile = farm.getFarmMap().getTile(x, y);

        if (tile.getType() != TileType.TILLED) {
            System.out.println("Tile bukan tanah yang bisa dikembalikan.");
            return;
        }

        boolean hasPickaxe = player.getInventory().getAllItems().keySet().stream()
            .anyMatch(item -> item instanceof Equipment && item.getName().equalsIgnoreCase("Pickaxe"));

        if (!hasPickaxe) {
            System.out.println("Butuh Pickaxe untuk melakukan recover.");
            return;
        }

        tile.setType(TileType.TILLABLE);
        tile.setCrop(null);
        tile.setWatered(false);

        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);
        System.out.println("Tile di (" + x + "," + y + ") dikembalikan ke bentuk awal.");
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
