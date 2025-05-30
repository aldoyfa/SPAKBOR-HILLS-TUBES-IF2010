package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_Caroline extends NPC {

    public NPC_Caroline(GamePanel gp) {
        this.gp = gp;
        name = "Caroline";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {"Firewood", "Coal",};
        likedItems = new String[] {"Potato", "Wheat"};
        hatedItems = new String[] {"Hot Pepper"};
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = ("Hai! Apakah kamu anak baru di tengah\nhutan itu? Aku akan main ke sana\nkapan-kapan ya!");
    }
}