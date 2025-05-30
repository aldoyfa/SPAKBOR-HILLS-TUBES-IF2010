package objects;

import main.GamePanel;

public class House extends Object {
    
    public House(GamePanel gp) {
        this.gp = gp;
        name = "House";
        width = 64*6; // 64 itu tileSize
        height = 64*6; // 64 itu tileSize
        super.getImage();
    }


}
