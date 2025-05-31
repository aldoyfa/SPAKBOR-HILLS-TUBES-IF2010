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
            "Parsnip Seeds", "Cauliflower Seeds", "Potato Seeds", "Wheat Seeds",
            "Blueberry Seeds", "Tomato Seeds", "Hot Pepper Seeds", "Melon Seeds",
            "Cranberry Seeds", "Pumpkin Seeds", "Grape Seeds"
        };
        likedItems = new String[] {"Catfish", "Salmon", "Sardine"};
        hatedItems = new String[] {"Coal", "Wood"};
        setDialogue();
    }

    @Override
    public void setDialogue() {
        dialogues[0] = "Welcome to my shop! I have everything you need for farming.";
        dialogues[1] = "I sell seeds, food, and useful tools for your farm!";
        dialogues[2] = "Thanks for visiting! Come back anytime you need supplies.";
    }
    
    @Override
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        
        // Set dialogue biasa dulu
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        
        // Setelah dialogue, langsung ke shop mode
        gp.ui.isEmilyShop = true; // Flag khusus Emily
        
        if (dialogueIndex >= dialogues.length || dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
    }
}