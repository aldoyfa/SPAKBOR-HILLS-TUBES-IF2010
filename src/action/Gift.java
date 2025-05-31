package action;

import objects.NPC;
import main.GamePanel;
import model.Item;

public class Gift implements Action {
    private GamePanel gp;
    private NPC npc;

    public Gift(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
        // Cek energy player terlebih dahulu
        if (gp.player.getEnergy() >= 5) {
            // Cek apakah inventory kosong
            if (gp.player.getInventory().getItems().isEmpty()) {
                gp.ui.currentDialogue = "You don't have any items to give!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // Masuk ke inventory selection state
            gp.ui.inventorySelectionIndex = 0; // Reset posisi selection
            gp.gameState = gp.inventorySelectionState;
        } else {
            // Not enough energy
            gp.ui.currentDialogue = "You don't have enough energy to give gifts!";
            gp.gameState = gp.dialogueState;
        }
    }
    
    // Method untuk execute gift logic saat item dipilih
    public static void executeGiftLogic(GamePanel gp) {
        java.util.List<model.Item> items = gp.player.getInventory().getItems();
        
        if (!items.isEmpty() && gp.ui.inventorySelectionIndex < items.size()) {
            NPC targetNPC = gp.ui.currentNPC;
            Item selectedItem = items.get(gp.ui.inventorySelectionIndex);
            
            // Execute gift logic menggunakan base name (tanpa quantity)
            String itemBaseName = selectedItem.getBaseName();
            String reaction = "";
            int heartPointsGain = 20; // Default value untuk sementara
            
            reaction = targetNPC.getName() + " accepts the " + itemBaseName + ". +" + heartPointsGain + " heart points!";
            
            // Update heart points NPC
            targetNPC.setHeartPoints(heartPointsGain);
            
            // Hapus 1 quantity dari item yang dipilih
            gp.player.getInventory().removeSelectedItem(1);
            
            // Update energy player
            gp.player.setEnergy(-5);
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}