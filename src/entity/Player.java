package entity;

import action.*;
import inputs.KeyboardListener;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import model.*;

public class Player {
    GamePanel gp;
    KeyboardListener keyH;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int worldX, worldY, speed;
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public final int screenX, screenY;
    public Inventory inventory = new Inventory();
    public Time time = new Time();

    private int energy = 100;

    public Player(GamePanel gp, KeyboardListener keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();  
        getPlayerImage();

        inventory.addItem(new model.Item("Hoe"));
        inventory.addItem(new model.Item("Parsnip Seeds x15"));
        inventory.addItem(new model.Item("Watering Can"));
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 17;
        worldY = gp.tileSize * 17;
        speed = 12;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkCollision() {
        int playerLeftWorldX = worldX + solidArea.x;
        int playerRightWorldX = worldX + solidArea.x + solidArea.width;
        int playerTopWorldY = worldY + solidArea.y;
        int playerBottomWorldY = worldY + solidArea.y + solidArea.height;

        collisionOn = false;

        int playerLeftCol = playerLeftWorldX / gp.tileSize;
        int playerRightCol = playerRightWorldX / gp.tileSize;
        int playerTopRow = playerTopWorldY / gp.tileSize;
        int playerBottomRow = playerBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (direction) {
            case "up":
                playerTopRow = (playerTopWorldY - speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "down":
                playerBottomRow = (playerBottomWorldY + speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
                tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "left":
                playerLeftCol = (playerLeftWorldX - speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTileNum[playerLeftCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "right":
                playerRightCol = (playerRightWorldX + speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[playerRightCol][playerTopRow];
                tileNum2 = gp.tileM.mapTileNum[playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
        }
    }

    public void update() {
        if (keyH.wPressed || keyH.aPressed || keyH.sPressed || keyH.dPressed) {
            if (keyH.wPressed) direction = "up";
            if (keyH.sPressed) direction = "down";
            if (keyH.aPressed) direction = "left";
            if (keyH.dPressed) direction = "right";

            collisionOn = false;
            checkCollision();

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 8) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        regenerateEnergy(1);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public int getEnergy() {
        return energy;
    }

    public void increaseEnergy(int amount) {
        energy += amount;
        if (energy > 100) energy = 100;
        System.out.println("Energi bertambah menjadi: " + energy);
    }

    public void reduceEnergy(int amount) {
        energy -= amount;
        if (energy < 0) energy = 0;
        System.out.println("Energi berkurang menjadi: " + energy);
    }

    public void regenerateEnergy(int amount) {
        if (energy < 100) {
            increaseEnergy(amount);
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }

    public void printStatus() {
        System.out.println("Posisi Player: (" + worldX + ", " + worldY + ")");
        System.out.println("Energi Player: " + energy);
    }

    // === ACTION METHODS === //
    public void eating(EdibleItem item) {
        new EatingAction(item).execute(this);
    }

    public void fish(String location) {
        new FishingAction(location, this.time).execute(this);
    }

    public void harvest() {
        new HarvestingAction().execute(this);
    }

    public void plant(GamePanel gp, int x, int y, Seed seed) {
        new PlantingAction(gp, x, y, seed).execute(this);
    }

    public void recover(GamePanel gp, int x, int y) {
        new RecoverLandAction(gp, x, y).execute(this);
    }

    public void till(GamePanel gp, int x, int y) {
        new TillingAction(gp, x, y).execute(this);
    }

    public void water(GamePanel gp, int x, int y) {
        new WateringAction(gp, x, y).execute(this);
    }
}
