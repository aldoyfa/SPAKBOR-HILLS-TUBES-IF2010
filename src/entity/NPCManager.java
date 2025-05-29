package entity;

import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPCManager {
    GamePanel gp;
    public ArrayList<NPC> npcList = new ArrayList<>();

    public NPCManager(GamePanel gp) {
        this.gp = gp;
        loadNPCs();
    }

    private void loadNPCs() {
        try {
            BufferedImage img = ImageIO.read(
                getClass().getResourceAsStream("/res/npc/merchant.png")
            );
            String[] dlg = {
                "Halo SPARTAN! Selamat datang.",
                "Kami menjual buah dan sayur segar.",
                "Tekan Enter untuk menutup dialog."
            };
            npcList.add(new NPC(10, 9, img, dlg, gp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (NPC npc : npcList) {
            int screenX = npc.worldX - gp.player.worldX + gp.player.screenX;
            int screenY = npc.worldY - gp.player.worldY + gp.player.screenY;
            g2.drawImage(npc.sprite, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public NPC checkCollisionNPC(Rectangle playerRect) {
        for (NPC npc : npcList) {
            if (playerRect.intersects(npc.solidArea)) {
                return npc;
            }
        }
        return null;
    }
}
