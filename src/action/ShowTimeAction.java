package action;

import main.GamePanel;

public class ShowTimeAction implements Action {
    private GamePanel gp;

    public ShowTimeAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Display time dan marriage status dalam dialogue
        String timeInfo = gp.farmMap.time.getCurrentTime();
        
        // TAMBAHAN: Include marriage status
        String marriageStatus = "";
        if (gp.player.isMarried()) {
            marriageStatus = "\n💒 Married to: " + gp.player.getPartner();
        } else {
            marriageStatus = "\n💔 Single";
        }
        
        gp.ui.currentDialogue = "⏰ CURRENT TIME ⏰\n" + timeInfo + marriageStatus;
        gp.gameState = gp.dialogueState;
        
        // Also log to console for debugging
        System.out.println("Time Display: " + timeInfo + " | Marriage: " + 
                          (gp.player.isMarried() ? gp.player.getPartner() : "Single"));
    }
}