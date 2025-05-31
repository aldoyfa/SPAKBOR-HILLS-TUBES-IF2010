package render;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import main.GamePanel;
import main.UtilityTool;
import model.Tile;

public class TileRenderer {
    GamePanel gp;
    

    public TileRenderer(GamePanel gp) {
        this.gp = gp;
    }

    public void getTileImage() {
        setup (0, "tilable", false);
        setup (1, "wall", true);
        setup (2, "tilled", false);
        setup (3, "tree", true);
        setup (4, "grass", false);
        setup (5, "planted", true);
        setup(6, "water", true);
        setup(7, "sand", false);
        setup(8, "lantai", false);
        setup(9, "void", true);
    }

    public void setup (int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            gp.farmMap.tile[index] = new Tile();
            gp.farmMap.tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName +".png"));
            gp.farmMap.tile[index].image = uTool.scaleImage(gp.farmMap.tile[index].image, gp.tileSize, gp.tileSize);
            gp.farmMap.tile[index].collision = collision;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            int tileNum = gp.farmMap.mapTileNum[col][row];

            int x = col * gp.tileSize;
            int y = row * gp.tileSize;
            int screenX = x - gp.player.worldX + gp.player.screenX;
            int screenY = y - gp.player.worldY + gp.player.screenY;

            if (x + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                x - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                y + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                y - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            
                g2.drawImage(gp.farmMap.tile[tileNum].image, screenX, screenY, null);
            }

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
}