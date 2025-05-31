package Objects;

import main.GamePanel;

public class ShippingBin extends Object {
    
    public ShippingBin(GamePanel gp) {
        this.gp = gp;
        name = "Shipping Bin";
        width = 64 * 3; // 64 itu tileSize
        height = 64 * 2; // 64 itu tileSize
        super.getImage();
    }
}
