package action;

import objects.NPC;
import main.GamePanel;
import model.RelationshipsStatus;

public class Marry implements Action {
    private GamePanel gp;
    private NPC npc;
    public Marry(GamePanel gp, NPC npc) {
        this.gp = gp;
        this.npc = npc;
        execute();
    }

    @Override
    public void execute() {
        // TAMBAHAN: Cek apakah player sudah married dengan NPC lain
        if (gp.player.isMarried()) {
            gp.ui.currentDialogue = "You are already married!\nYou can only marry one person.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Cek energy requirement (80)
        if (gp.player.getEnergy() < 80) {
            gp.ui.currentDialogue = "You don't have enough energy to get married!\n(Need 80 energy)";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Cek apakah player memiliki Proposal Ring
        if (!gp.player.hasItem("Proposal Ring")) {
            gp.ui.currentDialogue = "You need a Proposal Ring for the wedding ceremony!";
            gp.gameState = gp.dialogueState;
            return;
        }

        // Cek apakah NPC adalah fiance
        if (npc.getRelationshipStatus() != RelationshipsStatus.fiance) {
            if (npc.getRelationshipStatus() == RelationshipsStatus.spouse) {
                gp.ui.currentDialogue = "You are already married to " + npc.getName() + "!";
            } else {
                gp.ui.currentDialogue = "You need to propose to " + npc.getName() + " first!";
            }
            gp.gameState = gp.dialogueState;
            return;
        }

        // PERBAIKAN: Cek minimal 1 hari setelah engagement
        int currentDay = gp.farmMap.time.getDayActual();
        int engagementDay = npc.getEngagementDay();

        if (currentDay <= engagementDay) {
            gp.ui.currentDialogue = "You need to wait at least one day after engagement!\nCome back tomorrow to get married.";
            gp.gameState = gp.dialogueState;
            return;
        }

        // MARRIAGE CEREMONY
        // Update relationship status
        npc.setRelationshipStatus(RelationshipsStatus.spouse);
        gp.player.setPartner(npc.getName());
        gp.player.setMarried(true);

        // Energy cost: 80
        gp.player.setEnergy(-80);

        // TIME SKIP KE 22:00
        timeSkipToEvening();

        // PERBAIKAN: TELEPORT PLAYER KE SPAWN POSITION
        teleportPlayerToSpawn();

        String reaction = "💒 Wedding Ceremony Complete! 💒\n" +
                         "You and " + npc.getName() + " are now married!\n" +
                         "You spent the whole day celebrating together.\n" +
                         "It's now evening, and you're back home.";

        gp.ui.currentDialogue = reaction;
        gp.gameState = gp.dialogueState;

        System.out.println("MARRIAGE COMPLETE: " + gp.player.getName() + " married " + npc.getName());
    }

    // Method untuk time skip ke 22:00
    private void timeSkipToEvening() {
        // Set time ke 22:00 (10 PM)
        int currentHour = gp.farmMap.time.getHour();
        int targetHour = 22;

        // Calculate ticks needed to reach 22:00
        int hoursToSkip;
        if (currentHour <= targetHour) {
            hoursToSkip = targetHour - currentHour;
        } else {
            // Next day 22:00
            hoursToSkip = (24 - currentHour) + targetHour;
        }

        // Skip time (12 ticks = 1 hour)
        int ticksToSkip = hoursToSkip * 12;
        for (int i = 0; i < ticksToSkip; i++) {
            gp.farmMap.time.tick();
        }

        // Set exact time to 22:00
        gp.farmMap.time.setTimeToEvening();
    }

    // PERBAIKAN: Method untuk teleport player ke spawn position
    private void teleportPlayerToSpawn() {
        // Set player position ke spawn awal (sama seperti setDefaultValues)
        gp.player.worldX = gp.tileSize * 10;
        gp.player.worldY = gp.tileSize * 9;
        gp.player.direction = "down";

        System.out.println("Player teleported to spawn position: (" + gp.player.worldX + ", " + gp.player.worldY + ")");
    }
}