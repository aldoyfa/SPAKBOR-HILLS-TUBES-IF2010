package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_Emily extends NPC {

    public NPC_Emily(GamePanel gp) {
        this.gp = gp;
        name = "Emily";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {
            "Parsnip Seeds",
            "Cauliflower Seeds",
            "Potato Seeds",
            "Wheat Seeds",
            "Blueberry Seeds",
            "Tomato Seeds",
            "Hot Pepper Seeds",
            "Melon Seeds",
            "Cranberry Seeds",
            "Pumpkin Seeds",
            "Wheat Seeds",
            "Grape Seeds"
        };
        likedItems = new String[] {"Catfish", "Salmon", "Sardine"};
        hatedItems = new String[] {"Coal", "Wood"};
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = ("Selamat datang! Ada yang bisa aku bantu?\nAku Emily, senang bertemu denganmu!");
    }
}