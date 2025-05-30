package Objects;

import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class MayorTadi extends Object {
    public MayorTadi() {
        name = "House";
        width = 64*6; // 64 itu tileSize
        height = 64*6; // 64 itu tileSize
        solidArea = new Rectangle(0, 0, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/house.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
