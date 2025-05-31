package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_Abigail extends NPC {

    public NPC_Abigail(GamePanel gp) {
        this.gp = gp;
        name = "Abigail";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {"Blueberry", "Melon", "Pumpkin", "Grape", "Cranberry"};
        likedItems = new String[] {"Baguette", "Pumpkin Pie", "Wine"};
        hatedItems = new String[] {"Hot Pepper", "Cauliflower", "Parsnip", "Wheat"};
        setDialogue();
    }


    @Override
    public void setDialogue() {
        dialogues[0] = ("Heya! Aku Abigail! Mau eksplorasi alam \nbersamaku?");
    }
}