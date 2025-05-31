package objects;

import main.GamePanel;

public class Tv extends Object {
    
    public Tv(GamePanel gp) {
        this.gp = gp;
        name = "tv";
        width = 64*3; // 64 itu tileSize
        height = 64*3; // 64 itu tileSize
        super.getImage();
    }


}
