package model;

// import java.util.Random;
import main.GamePanel;

/**
 * Representasi peta Farm berukuran 32x32 tile.
 * Menyediakan fungsi penempatan objek, pengecekan collision,
 * dan visualisasi farm dengan posisi Player.
 */
public class FarmMap {
    GamePanel gp;
    // private Tile[][] tiles;
    // private final int width = 32;
    // private final int height = 32;
    private String name = "My Farm";
    private String player;
    public Time time = new Time();

    public FarmMap(GamePanel gp) {
        this.gp = gp;
        // tiles = new Tile[height][width];
        // placeHouse();
        // placePond();
        // placeShippingBinNearHouse();
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

    // private void placeHouse() {
    //     Random rand = new Random();
    //     int maxX = width - 6;
    //     int maxY = height - 6;
    //     int startX = rand.nextInt(maxX);
    //     int startY = rand.nextInt(maxY);

    //     for (int y = startY; y < startY + 6; y++) {
    //         for (int x = startX; x < startX + 6; x++) {
    //             tiles[y][x].setType(TileType.HOUSE);
    //         }
    //     }
    // }

    // private void placePond() {
    //     Random rand = new Random();
    //     int maxX = width - 4;
    //     int maxY = height - 3;
    //     int startX = rand.nextInt(maxX);
    //     int startY = rand.nextInt(maxY);

    //     for (int y = startY; y < startY + 3; y++) {
    //         for (int x = startX; x < startX + 4; x++) {
    //             tiles[y][x].setType(TileType.POND);
    //         }
    //     }
    // }

    // private void placeShippingBinNearHouse() {
    //     for (int y = 0; y < height - 2; y++) {
    //         for (int x = 0; x < width - 6; x++) {
    //             boolean isHouse = true;
    //             for (int j = y; j < y + 6; j++) {
    //                 for (int i = x; i < x + 6; i++) {
    //                     if (tiles[j][i].getType() != TileType.HOUSE) {
    //                         isHouse = false;
    //                         break;
    //                     }
    //                 }
    //                 if (!isHouse) break;
    //             }
    //             if (isHouse) {
    //                 for (int dy = 0; dy < 2; dy++) {
    //                     for (int dx = 0; dx < 3; dx++) {
    //                         tiles[y + dy][x + 6 + dx].setType(TileType.SHIPPING_BIN);
    //                     }
    //                 }
    //                 return;
    //             }
    //         }
    //     }
    // }

    // public Tile getTile(int x, int y) {
    //     return tiles[y][x];
    // }

    // /** Menampilkan farm map ke terminal. Player ditandai dengan 'p'. */
    // public void renderFarmMap(int playerX, int playerY) {
    //     for (int y = 0; y < height; y++) {
    //         for (int x = 0; x < width; x++) {
    //             if (x == playerX && y == playerY) {
    //                 System.out.print("p");
    //             } else {
    //                 switch (tiles[y][x].getType()) {
    //                     case HOUSE -> System.out.print("h");
    //                     case POND -> System.out.print("o");
    //                     case SHIPPING_BIN -> System.out.print("s");
    //                     case TILLED -> System.out.print("t");
    //                     case PLANTED -> System.out.print("l");
    //                     case OBSTACLE -> System.out.print("x");
    //                     default -> System.out.print(".");
    //                 }
    //             }
    //         }
    //         System.out.println();
    //     }
    // }
}