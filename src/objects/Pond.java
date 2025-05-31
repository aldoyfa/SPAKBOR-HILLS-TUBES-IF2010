package objects;

import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Pond extends Object {
    
    public Pond() {
        name = "Pond";
        width = 64 * 4; // 64 itu tileSize
        height = 64 * 3; // 64 itu tileSize
        solidArea = new Rectangle(0, 0, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/pond.png"));
            uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    
    }
}
