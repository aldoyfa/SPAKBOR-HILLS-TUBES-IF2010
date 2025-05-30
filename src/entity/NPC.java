package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import state.RelationshipState;
import observer.RelationshipObserver;
import main.GamePanel;

public class NPC {
    public int worldX, worldY;
    public BufferedImage sprite;
    public Rectangle solidArea;
    private String[] initDialog;
    private Map<Integer, String[]> actionDialogs; // key: 0=Chat,1=Gift,2=Propose,3=Marry
    private String name;
    private int heartPoints = 0;              // 0–150 :contentReference[oaicite:0]{index=0}
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private RelationshipState relationshipState;  // Single, Fiance, Spouse :contentReference[oaicite:1]{index=1}
    private List<RelationshipObserver> observers = new ArrayList<>();

    public NPC(String name,
               int col, int row,
               BufferedImage sprite,
               String[] initDialog,
               Map<Integer,String[]> actionDialogs,
               List<String> lovedItems,
               List<String> likedItems,
               List<String> hatedItems,
               RelationshipState initialState,
               GamePanel gp) {
        this.name             = name;
        this.worldX           = col * gp.tileSize;
        this.worldY           = row * gp.tileSize;
        this.sprite           = sprite;
        this.initDialog    = initDialog;
        this.actionDialogs = actionDialogs;
        this.lovedItems       = lovedItems;
        this.likedItems       = likedItems;
        this.hatedItems       = hatedItems;
        this.relationshipState= initialState;
        this.solidArea        = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);
    }

        // --- Getter dasar ---
    public String getName() { 
        return name; 
    }
    public int getHeartPoints() { 
        return heartPoints; 
    }
    public String[] getInitDialog() {
        return initDialog;
    }
    public String[] getActionDialog(int actionIndex) {
        return actionDialogs.get(actionIndex);
    }
    public String getRelationshipStatus(){ 
        return relationshipState.getStatusName(); 
    }

    // --- HeartPoints & Observer ---
    public void addObserver(RelationshipObserver o)   { 
        observers.add(o); 
    }
    private void notifyObservers() {
      for (RelationshipObserver o : observers) o.onRelationshipChanged(this);
    }
    public void changeHeartPoints(int delta) {
      heartPoints = Math.max(0, Math.min(150, heartPoints + delta));
      notifyObservers();
    }

    // --- Relationship State ---
    public void setRelationshipState(RelationshipState newState) {
      this.relationshipState = newState;
      notifyObservers();
    }
    public boolean canPropose() { 
        return relationshipState.canPropose() && heartPoints == 150; 
    }
    public boolean canMarry(){ 
        return relationshipState.canMarry(); 
    }

    // --- Evaluasi gift sesuai spesifikasi ---
    public int evaluateGift(String itemName) {
      if (lovedItems.contains(itemName)) {
        return +25;
      }
      if (likedItems.contains(itemName)) {
        return +20;
      }
      if (hatedItems.contains(itemName)) {
      return -25;
      }
      return 0;
    }
}

