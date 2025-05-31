package action;

import main.GamePanel;

public class ShowLocationAction implements Action {
    private GamePanel gp;

    public ShowLocationAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        int tileX = gp.player.worldX / gp.tileSize;
        int tileY = gp.player.worldY / gp.tileSize;

        System.out.println("Lokasi Anda saat ini:");
        System.out.println("Pixel: (" + gp.player.worldX + ", " + gp.player.worldY + ")");
        System.out.println("Tile: (" + tileX + ", " + tileY + ")");
        System.out.println("Map: " + gp.mapNames[gp.selectedMap]);

        gp.ui.currentDialogue = "Lokasi Anda saat ini:"
                + "\nPixel: (" + gp.player.worldX + ", " + gp.player.worldY + ")"
                + "\nTile: (" + tileX + ", " + tileY + ")"
                + "\nMap: " + gp.mapNames[gp.selectedMap];
        gp.gameState = gp.dialogueState;
    }
}