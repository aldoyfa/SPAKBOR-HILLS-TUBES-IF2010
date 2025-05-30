package Objects;

import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class ShippingBin extends Object {
    
    public ShippingBin() {
        name = "Shipping Bin";
        width = 64 * 3; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        solidArea = new Rectangle(0, 0, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/shippingbin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}