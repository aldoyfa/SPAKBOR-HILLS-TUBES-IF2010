package action;

import main.GamePanel;
import model.Item;
import model.ItemType;
import java.util.List;

public class EatAction implements Action {
    private GamePanel gp;

    public EatAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        List<Item> foodItems = gp.player.getInventory().getItemsByType(ItemType.FOOD);
            
        if (foodItems.isEmpty()) {
            gp.ui.currentDialogue = "You don't have any food to eat!";
            gp.gameState = gp.dialogueState;
            return;
        }
            
        // PERBAIKAN: LANGSUNG KE eatInventoryState
        gp.ui.filteredItems.clear();
        gp.ui.filteredItems.addAll(foodItems);
        gp.ui.filteredInventorySelectionIndex = 0;
        gp.gameState = gp.eatInventoryState; // LANGSUNG KE STATE KHUSUS
    }
    
    // Static method untuk execute eat logic - COMPLETELY INDEPENDENT
    public static void executeEatLogic(GamePanel gp) {
        if (gp.ui.filteredItems != null && !gp.ui.filteredItems.isEmpty() && 
            gp.ui.filteredInventorySelectionIndex < gp.ui.filteredItems.size()) {
            
            Item selectedFood = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
            
            // Logic untuk makan
            String reaction = "You ate " + selectedFood.getName() + "! ";
            int energyGain; // Default energy gain
            
            // Energy gain berdasarkan jenis makanan
            switch (selectedFood.getName().toLowerCase()) {
                case "fish n’ chips":
                    energyGain = 50;
                    break;
                case "baguette":
                    energyGain = 25;
                    break;
                case "sashimi":
                    energyGain = 70;
                    break;
                case "fugu":
                    energyGain = 50;
                    break;
                case "wine":
                    energyGain = 20;
                    break;
                case "pumpkin pie":
                    energyGain = 35;
                    break;
                case "veggie soup":
                    energyGain = 40;
                    break;
                case "fish stew":
                    energyGain = 70;
                    break;
                case "spakbor salad":
                    energyGain = 70;
                    break;
                case "fish sandwich":
                    energyGain = 50;
                    break;
                case "the legends of spakbor":
                    energyGain = 100;
                    break;
                case "cooked pig's head":
                    energyGain = 100;
                    break;
                default:
                    energyGain = 20;
            }
            
            reaction += "+" + energyGain + " energy!";
            
            // Update energy player
            gp.player.setEnergy(energyGain);
            
            // Hapus 1 quantity dari item
            gp.player.getInventory().removeItem(selectedFood.getBaseName(), 1);
            
            // PERBAIKAN: CLEAR FILTERED ITEMS TANPA currentAction
            gp.ui.filteredItems.clear();
            // HAPUS BARIS INI: gp.currentAction = "";
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}