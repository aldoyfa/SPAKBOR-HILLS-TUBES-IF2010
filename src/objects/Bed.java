package objects;

import main.GamePanel;

public class Bed extends Object {
    
    public Bed(GamePanel gp) {
        this.gp = gp;
        name = "bed";
        width = 64*4; // 64 itu tileSize
        height = 64*5; // 64 itu tileSize
        super.getImage();
    }


}
