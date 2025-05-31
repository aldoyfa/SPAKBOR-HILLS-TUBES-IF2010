package Objects;

import main.GamePanel;

public class ObjectManager {
    GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new House();
        gp.obj[0].worldX = 10 * gp.tileSize;
        gp.obj[0].worldY = 9 * gp.tileSize;

        gp.obj[1] = new ShippingBin();
        gp.obj[1].worldX = 17 * gp.tileSize;
        gp.obj[1].worldY = 14 * gp.tileSize;

        gp.obj[2] = new Pond();
        gp.obj[2].worldX = 23 * gp.tileSize;
        gp.obj[2].worldY = 14 * gp.tileSize;
    }
}
