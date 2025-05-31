package model;

public class Item {
    private String name;
    private int quantity;
    private ItemType type;

    // Constructor dengan type
    public Item(String name, int quantity, ItemType type) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public boolean removeQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false;
    }

    // Method untuk display dengan quantity (untuk UI sederhana)
    public String getSimpleDisplayName() {
        return (quantity > 1) ? name + " x" + quantity : name;
    }

    // Method untuk display dengan quantity dan type
    public String getDisplayName() {
        String displayName = (quantity > 1) ? name + " x" + quantity : name;
        return displayName + " [" + type.getDisplayName() + "]";
    }

    // Method untuk mendapatkan nama asli tanpa quantity
    public String getBaseName() {
        return name;
    }

    // Method untuk check apakah item stackable berdasarkan type
    public boolean isStackable() {
        return type != ItemType.EQUIPMENT && type != ItemType.MISC;
    }
}
