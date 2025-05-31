package objects;

import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.awt.Rectangle;

import model.RelationshipsStatus;
import main.GamePanel;
import main.UtilityTool;

public abstract class NPC extends Object {
    protected int heartPoints;
    protected String[] lovedItems;
    protected String[] likedItems;
    protected String[] hatedItems;
    protected RelationshipsStatus relationshipStatus;
    String[] dialogues = new String[10];
    int dialogueIndex = 0;
    
    // TAMBAHAN: Engagement tracking untuk marry requirement
    protected int engagementDay = 0;
    
    // TAMBAHAN: Utility tool untuk image scaling
    protected UtilityTool uTool = new UtilityTool();

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
    
    // TAMBAHAN: Engagement day methods
    public int getEngagementDay() {
        return engagementDay;
    }
    
    public void setEngagementDay(int day) {
        this.engagementDay = day;
    }

    // Metode untuk memperbarui status relationship berdasarkan heart points
    private void updateRelationshipStatus() {
        // Implementation bisa ditambahkan nanti
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