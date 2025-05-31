package action;

import entity.Player;
import model.Item;

public class HarvestingAction implements Action {

    private static class HarvestedCrop extends Item {
        public HarvestedCrop() {
            super("Harvested Crop");
        }
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk memanen.");
            return;
        }

        // Simplified harvesting logic based on inventory items
        boolean hasHarvestableItem = false;
        for (var item : player.getInventory().getItems()) {
            // Assuming harvestable items have "Seed" in their name for this example
            if (item.getName().toLowerCase().contains("seed")) {
                hasHarvestableItem = true;
                break;
            }
        }

        if (hasHarvestableItem) {
            // Add harvested item to inventory (example: "Harvested Crop")
            player.getInventory().addItem(new HarvestedCrop());
            player.reduceEnergy(5);
            System.out.println("Berhasil memanen tanaman.");
        } else {
            System.out.println("Belum bisa dipanen.");
        }
    }
}
