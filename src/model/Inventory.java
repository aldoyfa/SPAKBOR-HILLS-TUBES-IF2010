package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
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
}