package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();
    private int selectedIndex = 0;

    // Method untuk menambah item dengan smart stacking berdasarkan type
    public void addItem(Item item) {
        // Tools dan Quest items tidak di-stack
        if (!item.isStackable()) {
            items.add(item);
            return;
        }
        
        // Cari item dengan nama dan type yang sama untuk stacking
        Item existingItem = findStackableItem(item);
        
        if (existingItem != null) {
            existingItem.addQuantity(item.getQuantity());
        } else {
            items.add(item);
        }
    }

    // Method overload dengan explicit type
    public void addItem(String name, int quantity, ItemType type) {
        addItem(new Item(name, quantity, type));
    }

    // Helper method untuk mencari item berdasarkan nama
    private Item findItemByName(String name) {
        return items.stream()
            .filter(item -> item.getBaseName().equals(name))
            .findFirst()
            .orElse(null);
    }

    // Helper method untuk mencari item yang bisa di-stack
    private Item findStackableItem(Item item) {
        return items.stream()
            .filter(existingItem -> 
                existingItem.getBaseName().equals(item.getBaseName()) &&
                existingItem.getType() == item.getType() &&
                existingItem.isStackable())
            .findFirst()
            .orElse(null);
    }

    // Method untuk menghapus item dengan quantity
    public boolean removeItem(String name, int quantity) {
        Item item = findItemByName(name);
        if (item != null) {
            if (item.removeQuantity(quantity)) {
                // Jika quantity menjadi 0 atau kurang, hapus item dari list
                if (item.getQuantity() <= 0) {
                    items.remove(item);
                    // Adjust selected index jika perlu
                    if (selectedIndex >= items.size() && !items.isEmpty()) {
                        selectedIndex = items.size() - 1;
                    } else if (items.isEmpty()) {
                        selectedIndex = 0;
                    }
                }
                return true;
            }
        }
        return false;
    }

    // Method untuk menghapus 1 quantity dari item yang dipilih
    public boolean removeSelectedItem() {
        return removeSelectedItem(1);
    }

    // Method untuk menghapus quantity tertentu dari item yang dipilih
    public boolean removeSelectedItem(int quantity) {
        if (items.isEmpty() || selectedIndex >= items.size()) {
            return false;
        }
        
        Item selectedItem = items.get(selectedIndex);
        return removeItem(selectedItem.getBaseName(), quantity);
    }

    public List<Item> getItems() {
        return items;
    }

    public String getSelectedItemName() {
        if (items.isEmpty() || selectedIndex >= items.size()) {
            return null;
        }
        return items.get(selectedIndex).getDisplayName();
    }
    
    public Item getSelectedItem() {
        if (items.isEmpty() || selectedIndex >= items.size()) {
            return null;
        }
        return items.get(selectedIndex);
    }
    
    public void setSelectedIndex(int index) {
        if (index >= 0 && index < items.size()) {
            this.selectedIndex = index;
        }
    }
    
    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void printInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory kosong.");
        } else {
            System.out.println("Isi Inventory:");
            for (int i = 0; i < items.size(); i++) {
                String prefix = (i == selectedIndex) ? "> " : "  ";
                System.out.println(prefix + items.get(i).getDisplayName());
            }
        }
    }

    // Helper method untuk debug
    public boolean hasItem(String name) {
        return items.stream().anyMatch(item -> item.getName().equalsIgnoreCase(name));
    }

    public int getItemQuantity(String name) {
        Item item = findItemByName(name);
        return item != null ? item.getQuantity() : 0;
    }

    // Method untuk filter items by type
    public java.util.List<Item> getItemsByType(ItemType type) {
        return items.stream()
            .filter(item -> item.getType() == type)
            .collect(java.util.stream.Collectors.toList());
    }
}