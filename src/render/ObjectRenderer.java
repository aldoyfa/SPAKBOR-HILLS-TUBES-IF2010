package render;

import main.GamePanel;
import java.awt.Graphics2D;

public class ObjectRenderer {
    GamePanel gp;

    public ObjectRenderer(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        if (gp.newGameCounter == 1) {
            gp.farmMap.placeObjects();
        }
        else {
            gp.farmMap.placeObjects(); // Diisi nilai lokasi object dari save file
        }
    }

     public void draw(Graphics2D g2) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                int screenX = gp.obj[i].worldX - gp.player.worldX + gp.player.screenX;
                int screenY = gp.obj[i].worldY - gp.player.worldY + gp.player.screenY;
                if (gp.obj[i].worldX + gp.tileSize*6 > gp.player.worldX - gp.player.screenX &&
                gp.obj[i].worldX - gp.tileSize*6 < gp.player.worldX + gp.player.screenX &&
                gp.obj[i].worldY + gp.tileSize*6 > gp.player.worldY - gp.player.screenY &&
                gp.obj[i].worldY - gp.tileSize*6 < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(gp.obj[i].image, screenX, screenY, gp.obj[i].width, gp.obj[i].height, null);
                }
            }
        }
    }
}
