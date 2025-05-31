package Objects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Rectangle;
import main.GamePanel;
import main.UtilityTool;

public abstract class Object {
    GamePanel gp;
    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int worldX, worldY, width, height;
    public Rectangle solidArea; 
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();

    public void getImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/" + name +".png"));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        solidArea = new Rectangle(0, 0, width, height);
    }
}