package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import main.GamePanel;
import objects.House;
import objects.Pond;
import objects.ShippingBin;
import render.TileRenderer;

public class FarmMap {
    GamePanel gp;
    private String name = "My Farm";
    private String player;
    public Time time = new Time();
    public TileRenderer renderer;
    public int mapTileNum[][];
    public Tile[] tile;

    public FarmMap(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; 
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMap("/res/maps/farmmap.txt");
        renderer = new TileRenderer(this.gp);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = gp.player.getName();
    }

    public void loadMap(String filePath) {
        try {
            // Reset map array with current dimensions
            mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
            
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;
                
                String[] numbers = line.split(" ");
                for (col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++;
            }
            br.close();
            
            // Reinitialize renderer after loading new map
            if (renderer != null) {
                renderer.getTileImage();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void placeObjects() {
        Random rand = new Random();

        int houseX, houseY;
        do {
            houseX = rand.nextInt(21) + 10; 
            houseY = rand.nextInt(26) + 9;
        } while (
            (houseX == 10 && houseY == 9)
        );

        // Tempatkan House
        gp.obj[0] = new House(gp);
        gp.obj[0].worldX = houseX * gp.tileSize;
        gp.obj[0].worldY = houseY * gp.tileSize;

        // Cari posisi Shipping Bin (selalu berjarak 1 tile kosong dari House)
        int shippingBinX = houseX + 7;
        int shippingBinY = houseY + 5;
        gp.obj[1] = new ShippingBin(gp);
        gp.obj[1].worldX = shippingBinX * gp.tileSize;
        gp.obj[1].worldY = shippingBinY * gp.tileSize;

        int pondX, pondY;
        do {
            pondX = rand.nextInt(28) + 11; 
            pondY = rand.nextInt(29) + 10;
        } while (
            (pondX >= houseX && pondX <= houseX + 7 && pondY >= houseY && pondY <= houseY + 7) ||
            (pondX >= shippingBinX - 1 && pondX <= shippingBinX + 4 && pondY >= shippingBinY && pondY <= shippingBinY + 3)
        );
        
        gp.obj[2] = new Pond(gp);
        gp.obj[2].worldX = pondX * gp.tileSize;
        gp.obj[2].worldY = pondY * gp.tileSize; 
    }

    public void placeObjects(int houseX, int houseY, int shippingBinX, int shippingBinY, int pondX, int pondY) {

        gp.obj[0] = new House(gp);
        gp.obj[0].worldX = houseX * gp.tileSize;
        gp.obj[0].worldY = houseY * gp.tileSize;

        gp.obj[1] = new ShippingBin(gp);
        gp.obj[1].worldX = shippingBinX * gp.tileSize;
        gp.obj[1].worldY = shippingBinY * gp.tileSize;

        gp.obj[2] = new Pond(gp);
        gp.obj[2].worldX = pondX * gp.tileSize;
        gp.obj[2].worldY = pondY * gp.tileSize; 
    }
}
