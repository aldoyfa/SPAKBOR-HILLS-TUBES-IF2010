package objects;

import main.GamePanel;

public class Stove extends Object {
    
    public Stove(GamePanel gp) {
        this.gp = gp;
        name = "stove";
        width = 64*3; // 64 itu tileSize
        height = 64*3; // 64 itu tileSize
        super.getImage();
    }


}
