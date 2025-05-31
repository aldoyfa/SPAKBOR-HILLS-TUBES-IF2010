package objects;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

import model.RelationshipsStatus;
public abstract class NPC extends Object {
    protected int heartPoints;
    protected String[] lovedItems;
    protected String[] likedItems;
    protected String[] hatedItems;
    protected RelationshipsStatus relationshipStatus;
    String[] dialogues = new String[10];
    int dialogueIndex = 0; 

    public String getName() {
        return name;
    }

    public int getHeartPoints() {
        return heartPoints;
    }

    public String[] getLovedItems() {
        return lovedItems;
    }

    public String[] getLikedItems() {
        return likedItems;
    }

    public String[] getHatedItems() {
        return hatedItems;
    }

    public RelationshipsStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setHeartPoints(int heartPoints) {
        this.heartPoints += heartPoints;
    }

   public void setRelationshipStatus(RelationshipsStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    @Override
    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/NPC/" + name +".png"));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        solidArea = new Rectangle(0, 0, width, height);
    }

    public void setDialogue() {
        // Implementasikan di subclass
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }
}