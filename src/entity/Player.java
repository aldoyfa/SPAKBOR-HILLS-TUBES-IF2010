package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import inputs.KeyboardListener;
import main.GamePanel;
import main.UtilityTool;
import model.Inventory;
import model.Item;
import objects.NPC;
import objects.ShippingBin;
import model.ItemType;

public class Player {
    
   // SYSTEM
   GamePanel gp;
   KeyboardListener keyH;
   public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
   public String direction;
   public int spriteCounter = 0;
   public int spriteNum = 1; 
   int standCounter = 0;
   boolean moving = false;
   int pixelCounter = 0;
   public int worldX, worldY, speed;
   public Rectangle solidArea;
   public int solidAreaDefaultX, solidAreaDefaultY;
   public boolean collisionOn = false;
   public final int screenX, screenY;

   // Atribut Player in-Game
   private String name = "Player";
   private int energy = 100;
   private String farmName;
   public String gender;
   private String partner = null;
   private int gold = 0;
   public Inventory inventory = new Inventory();
   private boolean isMarried = false;
   private boolean isEngaged = false;
   private String favoriteItem = "Pickaxe" ;

    // STATISTICS
    public int totalIncome = 0;
    public int totalExpenditure = 0;
    public int cropsHarvested = 0;
    public int fishCaught = 0;
    public int commonFish = 0, regularFish = 0, legendaryFish = 0;
    public int daysPlayed = 0;
    public HashMap<NPC, NPCStats> npcStats = new HashMap<>();

   // Atribut tambahan
   public boolean hasSleptToday = false;

   public Player(GamePanel gp, KeyboardListener keyH) {
      this.gp = gp;
      this.keyH = keyH;

      screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
      screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

      solidArea = new Rectangle(1, 1, 62, 62);
      solidAreaDefaultX = solidArea.x;
      solidAreaDefaultY = solidArea.y;

      setDefaultValues();  
      getPlayerImage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy += energy;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = gp.farmMap.getName();
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getGold() {
        return gold;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        this.isMarried = married;
    }

    public boolean isEngaged() {
        return isEngaged;
    }

    public void setEngaged(boolean Engaged) {
        this.isEngaged = Engaged;
    }

    public void setGold(int gold) {
        this.gold += gold;
    }

    public String getGender() {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public String getFavoriteItem() {
        return favoriteItem;
    }

    public void setFavoriteItem(String favoriteItem) {
        this.favoriteItem = favoriteItem;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 9;
        speed = 16;
        direction = "down";
        
        // Menggunakan explicit types untuk clarity
        inventory.addItemByName("Parsnip Seeds", 15);
        inventory.addItemByName("Hoe", 1);
        inventory.addItemByName("Watering Can", 1);
        inventory.addItemByName("Pickaxe", 1);
        inventory.addItemByName("Fishing Rod", 1);
        inventory.addItemByName("Proposal Ring", 1);
        
        // // TAMBAHKAN FOOD ITEMS UNTUK TESTING
        // inventory.addItemByName("Baguette", 3);
        // inventory.addItemByName("Fish n' Chips", 2);
    }

    // Update getter methods untuk kompatibilitas dengan sistem lama
    public String getSelectedItemName() {
        return inventory.getSelectedItemName(); // Sudah return displayName (dengan quantity)
    }

    public Item getSelectedItem() {
        return inventory.getSelectedItem();
    }

    public boolean hasItem(String itemName) {
        return inventory.hasItem(itemName);
    }

    public boolean removeItem(String itemName) {
        return inventory.removeItem(itemName, 1);
    }

    public boolean removeItem(String itemName, int quantity) {
        return inventory.removeItem(itemName, quantity);
    }

    // Method untuk kemudahan menambah item
    public void addItem(String name, int quantity, ItemType type) {
        inventory.addItem(name, quantity, type);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void getPlayerImage() {
        up1 = setup("player_up_1"); 
        up2 = setup("player_up_2");
        down1 = setup("player_down_1");
        down2 = setup("player_down_2");
        left1 = setup("player_left_1");
        left2 = setup("player_left_2");
        right1 = setup("player_right_1");
        right2 = setup("player_right_2");
    }

    public void checkTileCollision() {
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
                tileNum1 = gp.farmMap.mapTileNum[playerLeftCol][playerTopRow];
                tileNum2 = gp.farmMap.mapTileNum[playerRightCol][playerTopRow];
                if (gp.farmMap.tile[tileNum1].collision || gp.farmMap.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "down":
                playerBottomRow = (playerBottomWorldY + speed) / gp.tileSize;
                tileNum1 = gp.farmMap.mapTileNum[playerLeftCol][playerBottomRow];
                tileNum2 = gp.farmMap.mapTileNum[playerRightCol][playerBottomRow];
                if (gp.farmMap.tile[tileNum1].collision || gp.farmMap.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "left":
                playerLeftCol = (playerLeftWorldX - speed) / gp.tileSize;
                tileNum1 = gp.farmMap.mapTileNum[playerLeftCol][playerTopRow];
                tileNum2 = gp.farmMap.mapTileNum[playerLeftCol][playerBottomRow];
                if (gp.farmMap.tile[tileNum1].collision || gp.farmMap.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            case "right":
                playerRightCol = (playerRightWorldX + speed) / gp.tileSize;
                tileNum1 = gp.farmMap.mapTileNum[playerRightCol][playerTopRow];
                tileNum2 = gp.farmMap.mapTileNum[playerRightCol][playerBottomRow];
                if (gp.farmMap.tile[tileNum1].collision || gp.farmMap.tile[tileNum2].collision) {
                    collisionOn = true;
                }
                break;
            default:
                return; 
        }

    }

    public int checkObjectCollision(Player player) {
        int index = -1;

        for (int j = 0; j < gp.obj.length; j++) {
            if (gp.obj[j] != null) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;

                gp.obj[j].solidArea.x = gp.obj[j].worldX + gp.obj[j].solidArea.x;
                gp.obj[j].solidArea.y = gp.obj[j].worldY + gp.obj[j].solidArea.y;
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.obj[j].solidArea)) {
                            if (gp.obj[j].collision) {
                                player.collisionOn = true;
                            }
                            index = j; 
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.obj[j].solidArea)) {
                            if (gp.obj[j].collision) {
                                player.collisionOn = true;
                            }
                            index = j; 
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.obj[j].solidArea)) {
                            if (gp.obj[j].collision) {
                                player.collisionOn = true;
                            }
                            index = j; 
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.obj[j].solidArea)) {
                            if (gp.obj[j].collision) {
                                player.collisionOn = true;
                            }
                            index = j; 
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.obj[j].solidArea.x = gp.obj[j].solidAreaDefaultX;
                gp.obj[j].solidArea.y = gp.obj[j].solidAreaDefaultY;
            }
        }
        return index;
    }

    public BufferedImage setup (String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void checkMapBoundary() {
        boolean atBoundary = false;
        
        // Cek batas map
        if (worldX <= 0) {
            worldX = 0;
            atBoundary = true;
        }
        if (worldX >= gp.maxWorldCol * gp.tileSize - gp.tileSize) {
            worldX = gp.maxWorldCol * gp.tileSize - gp.tileSize;
            atBoundary = true;
        }
        if (worldY <= 0) {
            worldY = 0;
            atBoundary = true;
        }
        if (worldY >= gp.maxWorldRow * gp.tileSize - gp.tileSize) {
            worldY = gp.maxWorldRow * gp.tileSize - gp.tileSize;
            atBoundary = true;
        }
        
        if (atBoundary) {
            moving = false;
            gp.gameState = gp.worldMapState;
        }
    }

    public void update() {
        if (moving == false) {
            if (keyH.wPressed || keyH.aPressed || keyH.sPressed || keyH.dPressed) {
                if (keyH.wPressed) {
                    direction = "up";
                }
                else if (keyH.sPressed) {
                    direction = "down";
                }
                else if (keyH.aPressed) {
                    direction = "left";
                }
                else if (keyH.dPressed) {
                    direction = "right";
                }
                moving = true;
        
                // Check tile collision    
                collisionOn = false;
                checkTileCollision();
                int objIndex = checkObjectCollision(this);
                if (objIndex != -1) {
                    interactObject(objIndex);
                }
            }
            else {
            standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
        if (moving == true) {
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
                if (spriteCounter > 8) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            pixelCounter += speed;
            if (pixelCounter == 64) {
                moving = false; 
                pixelCounter = 0;
            }
        }
        checkMapBoundary();
    }

    public void interactObject(int index) {
        if (gp.obj[index] instanceof NPC) {
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.NPCInterfaceState;
                gp.ui.currentNPC = (NPC) gp.obj[index];
            }
        }
        else if (gp.obj[index] instanceof ShippingBin) {
            if (gp.keyH.enterPressed == true) {
                // IMPORTANT: Reset currentNPC untuk object interactions
                gp.ui.currentNPC = null;

                ShippingBin shippingBin = (ShippingBin) gp.obj[index];
                shippingBin.interact();
            }
        gp.keyH.enterPressed = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

    public void addIncome(int amount) { gold += amount; totalIncome += amount; }
    public void addExpenditure(int amount) { gold -= amount; totalExpenditure += amount; }
    public void incrementCropsHarvested() { cropsHarvested++; }
    public void addFishCaught(String rarity) {
        fishCaught++;
        switch (rarity) {
            case "common": commonFish++; break;
            case "regular": regularFish++; break;
            case "legendary": legendaryFish++; break;
        }
    }
    public void incrementDaysPlayed() { daysPlayed++; }

    public static class NPCStats {
        public String relationship = "Single";
        public int chatFrequency = 0;
        public int giftFrequency = 0;
        public int visitingFrequency = 0;
    }

    public int seasonIncome = 0, seasonExpenditure = 0;
    public int seasonsPassed = 0;

    public void newSeason() {
        seasonsPassed++;
        seasonIncome = 0;
        seasonExpenditure = 0;
    }

    public double getAvgSeasonIncome() {
        return seasonsPassed == 0 ? 0 : (double) totalIncome / seasonsPassed;
    }
    public double getAvgSeasonExpenditure() {
        return seasonsPassed == 0 ? 0 : (double) totalExpenditure / seasonsPassed;
    }
}