package objects;

import model.RelationshipsStatus;
import main.GamePanel;

public class NPC_MayorTadi extends NPC {

    public NPC_MayorTadi(GamePanel gp) {
        this.gp = gp;
        name = "Mayor Tadi";
        width = 64 * 2; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
        heartPoints = 0;
        relationshipStatus = RelationshipsStatus.single;
        lovedItems = new String[] {"Legend"};
        likedItems = new String[] {"Angler", "Crimsonfish", "Glacierfish"};
        hatedItems = new String[] {
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
            "Grape Seeds",
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
            "Parsnip",
            "Cauliflower",
            "Potato",
            "Wheat",
            "Blueberry",
            "Tomato",
            "Hot Pepper",
            "Melon",
            "Cranberry",
            "Pumpkin",
            "Grape",
            "Fish n’ Chips",
            "Baguette",
            "Sashimi",
            "Fugu",
            "Wine",
            "Pumpkin Pie",
            "Veggie Soup",
            "Fish Stew",
            "Spakbor Salad",
            "Fish Sandwich",
            "The Legends of Spakbor",
            "Cooked Pig's Head",
            "Hoe",
            "Watering Can",
            "Pickaxe",
            "Fishing Rod",
            "Coal",
            "Firewood"
        };
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = ("ZEHAHAHA! Selamat datang di Spakbor\nHills! Aku harap kamu tidak murahan\ndan bisa bahagia di sini.");
    }
}