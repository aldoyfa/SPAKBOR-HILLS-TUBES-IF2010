package action;

import entity.Player;
import model.EdibleItem;

public class EatingAction implements Action {
    private EdibleItem item;

    public EatingAction(EdibleItem item) {
        this.item = item;
    }

    @Override
    public void execute(Player player) {
        if (!player.getInventory().contains(item)) {
            System.out.println("Item tidak tersedia.");
            return;
        }

        player.increaseEnergy(item.getEnergy());
        player.getInventory().remove(item);
        System.out.println("Energi ditambahkan sebanyak: " + item.getEnergy());
    }
}
