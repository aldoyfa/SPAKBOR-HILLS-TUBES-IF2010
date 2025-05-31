package action;

import objects.NPC;
import main.GamePanel;

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
            // NPC targetNPC = gp.ui.currentNPC; // Menggunakan currentNPC
            
            // Execute gift logic
            String reaction = "";
            int heartPointsGain = 20; // Default value untuk sementara
            reaction = gp.ui.currentNPC.getName() + " accepts the gift. +" + heartPointsGain + " heart points!";
            
            // Update heart points NPC
            gp.ui.currentNPC.setHeartPoints(heartPointsGain);
            
            // Hapus item dari inventory
            gp.player.getInventory().getItems().remove(gp.ui.inventorySelectionIndex);
            
            // Update energy player
            gp.player.setEnergy(-5);
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}