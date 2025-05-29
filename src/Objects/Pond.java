package Objects;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Pond extends Object {
    
    public Pond() {
        name = "Pond";
        width = 64 * 4; // 64 itu tileSize
        height = 64 * 3; // 64 itu tileSize
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/pond.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
