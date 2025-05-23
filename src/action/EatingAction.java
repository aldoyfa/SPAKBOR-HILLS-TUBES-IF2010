package action;
import java.time.LocalTime;

import model.Action;
import model.Farm;

public class EatingAction implements Action {
    private final LocalTime timeCost = LocalTime.of(0, 5);

    @Override
    public void execute(Player player, Farm farm, String args) {
        String itemName = args.trim();
        Inventory inventory = player.getInventory();

        Item toEat = null;
        for (Item item : inventory.getAllItems().keySet()) {
            if (item.getName().equalsIgnoreCase(itemName) && item instanceof Food) {
                toEat = item;
                break;
            }
        }

        if (toEat == null) {
            System.out.println("Item tidak ditemukan atau tidak bisa dimakan.");
            return;
        }

        Food food = (Food) toEat;
        inventory.removeItem(food, 1);
        player.addEnergy(food.getEnergyRestored());
        farm.advanceTime(timeCost);

        System.out.println("Kamu memakan " + food.getName() + " dan memulihkan energi sebanyak " + food.getEnergyRestored());
    }

    @Override
    public boolean isExecutable(Player player) {
        return true; // Energi tidak dicek karena makan justru memulihkan energi
    }
}
