// src/Objects/NPCObject.java
package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import entity.NPC;
import main.GamePanel;

public class NPCObject extends Object {
    private NPC npc;
    public GamePanel gp;

    public NPCObject(NPC npc, GamePanel gp) {
        this.npc   = npc;
        this.gp    = gp;
        // sesuaikan worldX/worldY/nama/solidArea/width/height
        this.image = npc.sprite;
        this.name  = npc.getName();
        this.worldX = npc.worldX;
        this.worldY = npc.worldY;
        this.width  = gp.tileSize;
        this.height = gp.tileSize;
        this.solidArea = new Rectangle(worldX, worldY, width, height);
        this.collision = true;
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        // Gambar sprite NPC di koordinat layar yang sama dengan Object.draw()
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(npc.sprite, screenX, screenY, width, height, null);
    }

    /** Expose NPC asli untuk interaksi di GamePanel */
    public NPC getNPC() {
        return npc;
    }
}
