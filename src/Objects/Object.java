package objects;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GamePanel;
import main.UtilityTool;

public class Object {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY, width, height;
    public Rectangle solidArea; 
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();


    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize*6 > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize*6 < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize*6 > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize*6 < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }
}
