// src/Objects/ObjectManager.java
package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Arrays;
import main.GamePanel;
import entity.NPC;
import state.SingleState;

public class ObjectManager {
    GamePanel gp;
    public Object[] obj;
    public int objCount = 0;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
        obj = new Object[20];  // atau jumlah maksimal object+NPC
        setObject();
    }

    public void setObject() {
        // --- Static objects ---
        obj[objCount] = new House();
        obj[objCount].worldX = 10 * gp.tileSize;
        obj[objCount].worldY = 9  * gp.tileSize;
        objCount++;

        obj[objCount] = new ShippingBin();
        obj[objCount].worldX = 17 * gp.tileSize;
        obj[objCount].worldY = 14 * gp.tileSize;
        objCount++;

        obj[objCount] = new Pond();
        obj[objCount].worldX = 23 * gp.tileSize;
        obj[objCount].worldY = 14 * gp.tileSize;
        objCount++;

        // --- Tambah NPC sebagai Objects.NPCObject ---
        try {
            BufferedImage mayorImg = ImageIO.read(
                getClass().getResourceAsStream("/res/npc/mayortadi.png")
            );
            // dialog awal dan loved/liked/hated sesuai spec
            String[] initD = {
                "Halo SPARTAN!", 
                "Saya Wali Kota Spakbor Hills."
            };
            NPC mayor = new NPC(
                "Mayor Tadi", 10, 9, mayorImg,
                initD,
                Arrays.asList("Legend"),
                Arrays.asList("Angler","Crimsonfish","Glacierfish"),
                Arrays.asList(/* semua item lain sebagai hated */),
                new SingleState(),
                gp
            );
            // Bungkus jadi NPCObject agar bisa masuk ke gp.obj[]
            obj[objCount] = new NPCObject(mayor, gp);
            objCount++;

            // Tambah NPC lain (Caroline, dst) dengan cara serupa...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < objCount; i++) {
            obj[i].draw(g2, gp);
        }
    }

    /**
     * Check collision antara player dan semua obj[i] yang punya collision=true.
     * Kembalikan indeks obj yang ketabrak, atau -1 jika tidak ada.
     */
    public int checkObjectCollision(Rectangle playerArea) {
        for (int i = 0; i < objCount; i++) {
            if (obj[i].collision && playerArea.intersects(obj[i].solidArea)) {
                return i;
            }
        }
        return -1;
    }
}
