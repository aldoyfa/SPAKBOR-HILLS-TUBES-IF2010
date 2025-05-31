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
    
    // EXISTING: Info untuk planted tiles
    public String plantedCrop;
    public int plantedDay;
    public int growthDays;
    
    // TAMBAHAN: Watering state
    public boolean isWatered = false;
    public int lastWateredDay = 0;

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
    
    // TAMBAHAN: Methods untuk plant info
    public boolean hasPlant() {
        return plantedCrop != null;
    }
    
    public void plantCrop(String cropType, int growthTime) {
        this.plantedCrop = cropType;
        this.growthDays = growthTime;
        this.plantedDay = getCurrentDay(); // Implement sesuai time system Anda
    }
    
    private int getCurrentDay() {
        // Implementasi berdasarkan gp.farmMap.time
        // Untuk sekarang return placeholder
        return 1;
    }
    
    // TAMBAHAN: Watering methods
    public void water(int currentDay) {
        this.isWatered = true;
        this.lastWateredDay = currentDay;
    }
    
    public boolean needsWatering(int currentDay) {
        // Plant needs watering if it hasn't been watered today
        return !isWatered || lastWateredDay < currentDay;
    }
    
    public void resetDailyWatering(int newDay) {
        // Reset watering status for new day
        if (lastWateredDay < newDay) {
            isWatered = false;
        }
    }
    
    // TAMBAHAN: Reset tile state saat recover
    public void resetToTillable() {
        this.plantedCrop = null;
        this.plantedDay = 0;
        this.growthDays = 0;
        this.isWatered = false;
        this.lastWateredDay = 0;
    }
}