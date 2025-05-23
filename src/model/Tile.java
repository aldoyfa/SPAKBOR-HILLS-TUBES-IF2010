package model;

public class Tile {
    private boolean isWatered = false;
    private TileType type;
    private Crops crop;

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        this.isWatered = watered;
    }

    public boolean canTill() {
        return this.type == TileType.TILLABLE;
    }

    public void setCrop(Crops crop) {
        this.crop = crop;
    }

    public Crops getCrop() {
        return this.crop;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return this.type;
    }
}
