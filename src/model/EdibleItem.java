package model;

public class EdibleItem extends Item {
    private int energy;

    public EdibleItem(String name, int energy) {
        super(name);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
}
