package model;

public class Item {
    private String name;
    private int quantity;

    // Constructor baru untuk memisahkan nama dan quantity
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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

    // Method untuk display dengan quantity (seperti UI yang sudah ada)
    public String getDisplayName() {
        if (quantity > 1) {
            return name + " x" + quantity;
        }
        return name;
    }

    // Method untuk mendapatkan nama asli tanpa quantity
    public String getBaseName() {
        return name;
    }
}
