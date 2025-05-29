package entity;

import java.awt.image.BufferedImage;
import main.GamePanel;
import java.awt.Rectangle;

public class NPC {
    public int worldX, worldY;
    public BufferedImage sprite;
    public Rectangle solidArea;
    public String[] dialogues;

    public NPC(int col, int row, BufferedImage sprite, String[] dialogues, GamePanel gp) {
        this.worldX    = col * gp.tileSize;
        this.worldY    = row * gp.tileSize;
        this.sprite    = sprite;
        this.dialogues = dialogues;
        this.solidArea = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);
    }

    public String getDialogue(int idx) {
        if (idx >= 0 && idx < dialogues.length) {
            return dialogues[idx];
        }
        return "";
    }

    public int getDialogueCount() {
        return dialogues.length;
    }
    
}
