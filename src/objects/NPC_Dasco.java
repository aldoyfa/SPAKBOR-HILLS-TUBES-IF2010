package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_Dasco extends NPC {

    public NPC_Dasco(GamePanel gp) {
        this.gp = gp;
        name = "Dasco";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {"The Legends of Spakbor", "Cooked Pig's Head", "Wine", "Fugu", "Spakbor Salad"};
        likedItems = new String[] {"Fish Sandwich", "Fish Stew", "Baguette", "Fish n’ Chips"};
        hatedItems = new String[] {"Legend", "Grape", "Cauliflower", "Wheat", "Pufferfish", "Salmon"};
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = ("Hai bos! Mau kaya dengan cepat? Sini!\nDijamin gacor 999x!");
    }
}