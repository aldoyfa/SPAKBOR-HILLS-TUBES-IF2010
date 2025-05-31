package model;

import java.awt.image.BufferedImage;

/**
 * Merepresentasikan sebuah petak (tile) dalam FarmMap.
 * Setiap tile memiliki posisi koordinat (x, y) dan tipe (TileType).
 * Tile dapat diperiksa apakah dapat dilewati oleh Player atau tidak.
 */
public class Tile {
    private TileType type;
    public BufferedImage image;
    public boolean collision = false;

    public Tile() {
       
    }

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}