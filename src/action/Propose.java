package action;

import objects.NPC;
import main.GamePanel;
import model.RelationshipsStatus;

public class Propose implements Action {
    private GamePanel gp;
    private NPC npc;

    public Propose(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
        // TAMBAHAN: Cek apakah player sudah married
        if (gp.player.isMarried()) {
            gp.ui.currentDialogue = "You are already married to " + gp.player.getPartner() + "!\nYou can only marry one person.";
            gp.gameState = gp.dialogueState;
            return;
        }
        
        // Cek apakah player memiliki Proposal Ring
        if (!gp.player.hasItem("Proposal Ring")) {
            gp.ui.currentDialogue = "You need a Proposal Ring to propose!\nGet one from the store first.";
            gp.gameState = gp.dialogueState;
            return;
        }
        
        // Cek apakah NPC sudah married atau engaged dengan player lain
        if (npc.getRelationshipStatus() == RelationshipsStatus.spouse) {
            gp.ui.currentDialogue = npc.getName() + " is already married!";
            gp.gameState = gp.dialogueState;
            return;
        }
        
        if (npc.getRelationshipStatus() == RelationshipsStatus.fiance) {
            gp.ui.currentDialogue = npc.getName() + " is already your fiance!\nYou can marry them tomorrow.";
            gp.gameState = gp.dialogueState;
            return;
        }
        
        // PERBAIKAN: Cek heart points requirement (150)
        if (npc.getHeartPoints() >= 150) {
            // LAMARAN DITERIMA
            npc.setRelationshipStatus(RelationshipsStatus.fiance);
            
            // Set engagement day untuk tracking marry requirement
            npc.setEngagementDay(gp.farmMap.time.getDayActual());
            
            // Energy cost: 10 untuk lamaran diterima
            gp.player.setEnergy(-10);
            
            // Time cost: 1 jam (12 ticks = 60 menit)
            for (int i = 0; i < 12; i++) {
                gp.farmMap.time.tick();
            }
            
            String reaction = "💍 " + npc.getName() + " accepted your proposal!\n" +
                             "\"Yes! I will marry you!\"\n" +
                             "You are now engaged! You can marry tomorrow.";
            
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
            
            System.out.println("PROPOSAL ACCEPTED: " + npc.getName() + " is now fiance!");
            
        } else {
            // LAMARAN DITOLAK
            // Energy cost: 20 untuk lamaran ditolak
            gp.player.setEnergy(-20);
            
            // Time cost: 1 jam (12 ticks = 60 menit)
            for (int i = 0; i < 12; i++) {
                gp.farmMap.time.tick();
            }
            
            int neededPoints = 150 - npc.getHeartPoints();
            String reaction = "💔 " + npc.getName() + " rejected your proposal.\n" +
                             "\"I'm sorry, but I don't feel ready yet...\"\n" +
                             "You need " + neededPoints + " more heart points.";
            
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
            
            System.out.println("PROPOSAL REJECTED: " + npc.getName() + " needs " + neededPoints + " more heart points");
        }
    }
}