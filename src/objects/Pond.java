package Objects;

import main.GamePanel;

public class Pond extends Object {
    
    public Pond(GamePanel gp) {
        this.gp = gp;
        name = "Pond";
        width = 64 * 4; // 64 itu tileSize
        height = 64 * 3; // 64 itu tileSize
        super.getImage();
    }
}
