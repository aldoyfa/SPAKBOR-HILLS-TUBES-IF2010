package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();
    private int selectedIndex = 0;

    // Method untuk menambah item dengan smart stacking
    public void addItem(Item item) {
        // Cari apakah item dengan nama yang sama sudah ada
        Item existingItem = findItemByName(item.getBaseName());
        
        if (existingItem != null) {
            // Jika ada, tambahkan quantity ke item yang sudah ada
            existingItem.addQuantity(item.getQuantity());
        } else {
            // Jika tidak ada, tambahkan item baru
            items.add(item);
        }
    }

    // Method overload untuk kemudahan (nama, quantity)
    public void addItem(String name, int quantity) {
        addItem(new Item(name, quantity));
    }

    // Method overload untuk backward compatibility (single item)
    public void addItem(String name) {
        addItem(new Item(name, 1));
    }

    // Helper method untuk mencari item berdasarkan nama
    private Item findItemByName(String name) {
        return items.stream()
            .filter(item -> item.getBaseName().equals(name))
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
        return findItemByName(name) != null;
    }

    public int getItemQuantity(String name) {
        Item item = findItemByName(name);
        return item != null ? item.getQuantity() : 0;
    }
}