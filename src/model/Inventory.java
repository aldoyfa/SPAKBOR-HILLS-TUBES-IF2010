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

    // ENHANCED: Method dengan database auto-detection
    public void addItem(String name, int quantity, ItemType type) {
        addItem(new Item(name, quantity, type)); // Item constructor sudah handle database lookup
    }
    
    // TAMBAHAN: Method untuk add item by name saja (auto-detect type)
    public void addItemByName(String name, int quantity) {
        Item item = Item.create(name, quantity);
        if (item != null) {
            addItem(item);
        } else {
            System.out.println("Error: Item '" + name + "' not found in database!");
        }
    }

    // EXISTING methods tetap sama
    private Item findItemByName(String name) {
        return items.stream()
            .filter(item -> item.getBaseName().equals(name))
            .findFirst()
            .orElse(null);
    }

    private Item findStackableItem(Item item) {
        return items.stream()
            .filter(existingItem -> 
                existingItem.getBaseName().equals(item.getBaseName()) &&
                existingItem.getType() == item.getType() &&
                existingItem.isStackable())
            .findFirst()
            .orElse(null);
    }

    public boolean removeItem(String name, int quantity) {
        Item item = findItemByName(name);
        if (item != null) {
            if (item.removeQuantity(quantity)) {
                if (item.getQuantity() <= 0) {
                    items.remove(item);
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

    public boolean removeSelectedItem() {
        return removeSelectedItem(1);
    }

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

    public boolean hasItem(String name) {
        return findItemByName(name) != null;
    }

    // public int getItemQuantity(String name) {
    //     Item item = findItemByName(name);
    //     return item != null ? item.getQuantity() : 0;
    // }

    // NEW: Get quantity of specific item
    public int getItemQuantity(String itemName) {
        Item item = findItemByName(itemName);
        return item != null ? item.getQuantity() : 0;
    }

    // NEW: Get items by type (for sell categories)
    public List<Item> getItemsByType(ItemType type) {
        return items.stream()
            .filter(item -> item.getType() == type)
            .collect(java.util.stream.Collectors.toList());
    }

    public boolean hasItem(String name, int quantity) {
        Item item = findItemByName(name);
        return item != null && item.getQuantity() >= quantity;
    }
}