package model;

import java.util.Objects;

public class Seed extends Item {
    private String description;
    private int growthDays;
    private boolean stackable;

    public Seed(String name, String description, int growthDays, boolean stackable) {
        super(name);
        this.description = description;
        this.growthDays = growthDays;
        this.stackable = stackable;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getGrowthDays() {
        return growthDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seed)) return false;
        Seed seed = (Seed) o;
        return growthDays == seed.growthDays &&
                stackable == seed.stackable &&
                Objects.equals(getName(), seed.getName()) &&
                Objects.equals(description, seed.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), description, growthDays, stackable);
    }

    @Override
    public String toString() {
        return "Seed{" +
                "name='" + getName() + '\'' +
                ", description='" + description + '\'' +
                ", growthDays=" + growthDays +
                ", stackable=" + stackable +
                '}';
    }
}
