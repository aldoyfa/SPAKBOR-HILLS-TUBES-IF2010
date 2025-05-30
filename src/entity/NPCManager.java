package entity;

import java.util.List;
import java.util.Map;
import java.util.Arrays;       // untuk Arrays.asList(...)
import java.util.HashMap;
// import java.util.ArrayList;    // jika Anda mau pakai new ArrayList<>()
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import state.SingleState;
import state.FianceState;
import state.SpouseState;

public class NPCManager {
    GamePanel gp;
    public List<NPC> npcList;

    public NPCManager(GamePanel gp) {
        this.gp = gp;
        loadNPCs();
    }

    private void loadNPCs() {
        try {
            // Contoh: Mayor Tadi
            BufferedImage mayorImg = ImageIO.read(
              getClass().getResourceAsStream("/res/npc/mayortadi.png")
            );
            List<String> mayorLoved  = Arrays.asList("Legend");
            List<String> mayorLiked  = Arrays.asList("Angler","Crimsonfish","Glacierfish");
            List<String> mayorHated  = Arrays.asList(/* seluruh item lain */);
            String[] mayorInit = {
            "Halo SPARTAN! Selamat datang di kota saya."
            };
            Map<Integer,String[]> mayorActions = new HashMap<>();
            mayorActions.put(0, new String[]{"Senang mengobrol denganku?"});
            mayorActions.put(1, new String[]{"Terima kasih atas hadiahnya!"});
            mayorActions.put(2, new String[]{"Aku akan memikirkannya..."});
            mayorActions.put(3, new String[]{"Selamat menempuh hidup baru!"});
            
            NPC mayor = new NPC(
              "Mayor Tadi",  10,  9, mayorImg,
              mayorInit, mayorActions,
              mayorLoved, mayorLiked, mayorHated,
              new SingleState(), gp
            );

            // Contoh: Caroline
            // BufferedImage carImg = ImageIO.read(
            //   getClass().getResourceAsStream("/res/npc/caroline.png")
            // );
            // NPC caroline = new NPC(
            //   "Caroline",  12,  8, carImg,
            //   new String[]{"Hai, aku tukang kayu lokal."},
            //   Arrays.asList("Firewood","Coal"),
            //   Arrays.asList("Potato","Wheat"),
            //   Arrays.asList("Hot Pepper"),
            //   new SingleState(), gp
            // );

            npcList = Arrays.asList(mayor /*, ...NPC lainnya...*/);
        }
        catch (Exception e) { e.printStackTrace(); }
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
