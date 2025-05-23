package action;

import main.GameModel;
import model.Inventory;
import model.Player;
import item.Item;
import java.util.Map;

public class OpenInventoryAction implements Action {
    @Override
    public void execute(GameModel game) {
        Player player = game.getPlayer();
        Inventory inventory = player.getInventory();
        Map<Item, Integer> items = inventory.getAllItems();

        if (items.isEmpty()) {
            System.out.println("Inventory kosong.");
        } else {
            System.out.println("Isi Inventory:");
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                System.out.println("- " + entry.getKey().getName() + " x" + entry.getValue());
            }
        }
    }
} 
