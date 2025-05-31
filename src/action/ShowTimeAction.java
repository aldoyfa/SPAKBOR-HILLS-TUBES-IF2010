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
        System.out.println(gp.farmMap.time.getCurrentTime());
        // Display time dan marriage status dalam dialogue
        String timeInfo = gp.farmMap.time.getCurrentTime();

        // TAMBAHAN: Include marriage status
        String marriageStatus = "";
        if (gp.player.isMarried()) {
            marriageStatus = "\nMarried to: " + gp.player.getPartner();
        } else if (gp.player.isEngaged()) {
            marriageStatus = "\nEngaged to: " + gp.player.getPartner();
        } else {
            marriageStatus = "\nRelationship: Single";
        }

        gp.ui.currentDialogue = "CURRENT TIME\n" + timeInfo + marriageStatus;
        gp.gameState = gp.dialogueState;

        // Also log to console for debugging
        System.out.println("Time Display: " + timeInfo + " | Marriage: " + 
                          (gp.player.isMarried() ? gp.player.getPartner() : "Single"));
    }
}
