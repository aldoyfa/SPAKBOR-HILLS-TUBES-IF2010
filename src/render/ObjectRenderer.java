package render;

import main.GamePanel;
import objects.NPC_Abigail;
import objects.NPC_Caroline;
import objects.NPC_Dasco;
import objects.NPC_Emily;
import objects.NPC_MayorTadi;
import objects.NPC_Perry;

import java.awt.Graphics2D;

public class ObjectRenderer {
    GamePanel gp;

    public ObjectRenderer(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        if (gp.newGameCounter == 0) {
            gp.farmMap.placeObjects();
        }
        else {
            gp.farmMap.placeObjects(); // Diisi nilai lokasi object dari save file
        }
    
        gp.obj[3] = new NPC_Abigail(gp);
        gp.obj[3].worldX = 30 * gp.tileSize;
        gp.obj[3].worldY = 14 * gp.tileSize;

        gp.obj[4] = new NPC_Caroline(gp);
        gp.obj[4].worldX = 30 * gp.tileSize;
        gp.obj[4].worldY = 17 * gp.tileSize;

        gp.obj[5] = new NPC_Emily(gp);
        gp.obj[5].worldX = 30 * gp.tileSize;
        gp.obj[5].worldY = 20 * gp.tileSize;

        gp.obj[6] = new NPC_Dasco(gp);
        gp.obj[6].worldX = 30 * gp.tileSize;
        gp.obj[6].worldY = 23 * gp.tileSize;

        gp.obj[7] = new NPC_Perry(gp);
        gp.obj[7].worldX = 30 * gp.tileSize;
        gp.obj[7].worldY = 26 * gp.tileSize;

        gp.obj[8] = new NPC_MayorTadi(gp);
        gp.obj[8].worldX = 30 * gp.tileSize;
        gp.obj[8].worldY = 29 * gp.tileSize;
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
