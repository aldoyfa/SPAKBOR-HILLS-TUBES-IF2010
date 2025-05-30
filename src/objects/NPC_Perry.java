package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_Perry extends NPC {

    public NPC_Perry(GamePanel gp) {
        this.gp = gp;
        name = "Perry";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {"Cranberry", "Blueberry"};
        likedItems = new String[] {"Wine"};
        hatedItems = new String[] {
            "Bullhead",
            "Carp",
            "Chub",
            "Largemouth Bass",
            "Rainbow Trout",
            "Sturgeon",
            "Midnight Carp",
            "Flounder",
            "Halibut",
            "Octopus",
            "Pufferfish",
            "Sardine",
            "Super Cucumber",
            "Catfish",
            "Salmon",
            "Angler",
            "Crimsonfish",
            "Glacierfish",
            "Legend"
        };
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = ("O-oh.. Halo..\nUmm... Aku Perry. Mau tanda tangan?");
    }
}