package action;

import main.GamePanel;
import model.Item;
import model.ItemType;
import java.util.List;

public class PlantAction implements Action {
    private GamePanel gp;

    public PlantAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Cek energy player terlebih dahulu
        if (gp.player.getEnergy() >= 10) {
            // Filter item SEED dari inventory
            List<Item> seedItems = gp.player.getInventory().getItemsByType(ItemType.SEED);
            
            if (seedItems.isEmpty()) {
                gp.ui.currentDialogue = "You don't have any seeds to plant!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // PERBAIKAN: LANGSUNG KE plantInventoryState
            gp.ui.filteredItems.clear();
            gp.ui.filteredItems.addAll(seedItems);
            gp.ui.filteredInventorySelectionIndex = 0;
            gp.gameState = gp.plantInventoryState; // LANGSUNG KE STATE KHUSUS
        } else {
            gp.ui.currentDialogue = "You don't have enough energy to plant!";
            gp.gameState = gp.dialogueState;
        }
    }
    
    // Static method untuk execute plant logic - COMPLETELY INDEPENDENT
    public static void executePlantLogic(GamePanel gp) {
        if (gp.ui.filteredItems != null && !gp.ui.filteredItems.isEmpty() && 
            gp.ui.filteredInventorySelectionIndex < gp.ui.filteredItems.size()) {
            
            Item selectedSeed = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
            
            // Logic untuk menanam
            String reaction = "You planted " + selectedSeed.getName() + "! ";
            
            // Determine crop result
            String cropResult = "";
            switch (selectedSeed.getName().toLowerCase()) {
                case "parsnip seeds":
                    cropResult = "\nParsnip will grow in 4 days.";
                    break;
                case "cauliflower seeds":
                    cropResult = "\nCauliflower will grow in 12 days.";
                    break;
                default:
                    cropResult = "\nSeeds have been planted.";
            }
            
            reaction += cropResult;
            
            // Hapus 1 quantity dari seeds
            gp.player.getInventory().removeItem(selectedSeed.getBaseName(), 1);
            
            // Update energy cost untuk planting
            gp.player.setEnergy(-10);
            
            // PERBAIKAN: CLEAR FILTERED ITEMS TANPA currentAction
            gp.ui.filteredItems.clear();
            // HAPUS BARIS INI: gp.currentAction = "";
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}