package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import inputs.KeyboardListener;
import main.GamePanel;

public class Player {
   GamePanel gp;
   KeyboardListener keyH;
   public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
   public String direction;
   public int spriteCounter = 0;
   public int spriteNum = 1; 
   public int worldX, worldY, speed;
   public Rectangle solidArea;
   public int solidAreaDefaultX, solidAreaDefaultY;
   public boolean collisionOn = false;
   public final int screenX, screenY;

   public Player(GamePanel gp, KeyboardListener keyH) {
      this.gp = gp;
      this.keyH = keyH;

      screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
      screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

      solidArea = new Rectangle(8, 16, 32, 32);
      solidAreaDefaultX = solidArea.x;
      solidAreaDefaultY = solidArea.y;

      setDefaultValues();  
      getPlayerImage();
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
            default:
                return; // No movement, no collision check
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

    public void update() {
        if (keyH.wPressed || keyH.aPressed || keyH.sPressed || keyH.dPressed) {
            if (keyH.wPressed) {
                direction = "up";
            }
            if (keyH.sPressed) {
                direction = "down";
            }
            if (keyH.aPressed) {
                direction = "left";
            }
            if (keyH.dPressed) {
                direction = "right";
            }
        
            // Check tile collision    
            collisionOn = false;
            checkTileCollision();
            int objIndex = checkObjectCollision(this);
            if (objIndex != -1) {
                pickUpObject(objIndex);
            }   

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
        }
    }

    public void pickUpObject(int index) {
        // implement logic ketika player menyentuh objek di sini
        // contoh:
        if (gp.obj[index].name.equals("House")) {
            System.out.println("You entered the house!");
        } else if (gp.obj[index].name.equals("Shipping Bin")) {
            System.out.println("You opened the shipping bin!");
        } else if (gp.obj[index].name.equals("Pond")) {
            System.out.println("You are at the pond!");
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
