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
            
            // PERBAIKAN: SET currentNPC sebelum masuk ke inventory selection
            gp.ui.currentNPC = this.npc;
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
            NPC targetNPC = gp.ui.currentNPC; // Menggunakan currentNPC
            model.Item selectedItem = items.get(gp.ui.inventorySelectionIndex);
            
            // Execute gift logic menggunakan base name
            String itemBaseName = selectedItem.getBaseName();
            String reaction = "";
            int heartPointsGain = 20; // Default value
            
            // Evaluasi berdasarkan NPC preference
            String[] lovedItems = targetNPC.getLovedItems();
            String[] likedItems = targetNPC.getLikedItems();
            String[] hatedItems = targetNPC.getHatedItems();
            
            // Check if item is loved, liked, or hated
            boolean isLoved = java.util.Arrays.asList(lovedItems).contains(itemBaseName);
            boolean isLiked = java.util.Arrays.asList(likedItems).contains(itemBaseName);
            boolean isHated = java.util.Arrays.asList(hatedItems).contains(itemBaseName);
            
            if (isLoved) {
                heartPointsGain = 50;
                reaction = targetNPC.getName() + " loves the " + itemBaseName + "! +" + heartPointsGain + " heart points!";
            } else if (isLiked) {
                heartPointsGain = 30;
                reaction = targetNPC.getName() + " likes the " + itemBaseName + "! +" + heartPointsGain + " heart points!";
            } else if (isHated) {
                heartPointsGain = -20;
                reaction = targetNPC.getName() + " hates the " + itemBaseName + "! " + heartPointsGain + " heart points!";
            } else {
                heartPointsGain = 10;
                reaction = targetNPC.getName() + " accepts the " + itemBaseName + ". +" + heartPointsGain + " heart points!";
            }
            
            // Update heart points NPC
            targetNPC.setHeartPoints(heartPointsGain);
            
            // Hapus 1 quantity dari item yang dipilih
            gp.player.getInventory().removeItem(selectedItem.getBaseName(), 1);
            
            // Update energy player
            gp.player.setEnergy(-5);
            
            // PERBAIKAN: JANGAN RESET currentNPC - BIARKAN UNTUK DIALOGUE
            // gp.ui.currentNPC = null; // HAPUS BARIS INI
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}