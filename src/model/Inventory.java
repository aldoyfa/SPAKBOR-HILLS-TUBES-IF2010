package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean contains(Object item) {
        return items.contains(item);
    }

    public void remove(Object item) {
        items.remove(item);
    }

    // Untuk memeriksa apakah ada item dengan nama tertentu
    public boolean hasItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Jika ingin spesifik cek tool saja (tidak perlu jika belum ada turunan Tool)
    public boolean hasTool(String toolName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(toolName)) {
                return true;
            }
        }
        return false;
    }

    // Untuk mengambil item berdasarkan nama
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return items;
    }

    public void printInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory kosong.");
        } else {
            System.out.println("Isi Inventory:");
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
        }
    }
    public EdibleItem getSelectedEdibleItem() {
        for (Item item : items) {
            if (item instanceof EdibleItem) {
                return (EdibleItem) item;
            }
        }
        return null;
    }
    
}
