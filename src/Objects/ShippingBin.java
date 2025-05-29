package Objects;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ShippingBin extends Object {
    
    public ShippingBin() {
        name = "ShippingBin";
        width = 64 * 3; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/shippingbin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
