package model;

/**
 * Merepresentasikan sebuah petak (tile) dalam FarmMap.
 * Setiap tile memiliki posisi koordinat (x, y) dan tipe (TileType).
 * Tile dapat diperiksa apakah dapat dilewati oleh Player atau tidak.
 */
public class Tile {
    private int x;
    private int y;
    private TileType type;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Mengecek apakah tile ini bisa dilalui (walkable).
     * Hanya tile bertipe TILLABLE, TILLED, dan PLANTED yang bisa dilewati.
     */
    public boolean isWalkable() {
        return type == TileType.TILLABLE || type == TileType.TILLED || type == TileType.PLANTED;
    }
}