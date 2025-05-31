// src/entity/GameObject.java
package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

public abstract class GameObject {
    public int worldX, worldY;
    public Rectangle solidArea;
    public boolean collision = false;

    /** 
     * Gambar object atau NPC. 
     * Disebut dari ObjectManager.draw() 
     */
    public abstract void draw(Graphics2D g2, GamePanel gp);
}
