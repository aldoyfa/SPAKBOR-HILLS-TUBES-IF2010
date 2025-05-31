package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();
    private int selectedIndex = 0; // Index item yang sedang dipilih

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public String getSelectedItemName() {
        if (items.isEmpty() || selectedIndex >= items.size()) {
            return null;
        }
        return items.get(selectedIndex).getName();
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
    
    public boolean removeSelectedItem() {
        if (items.isEmpty() || selectedIndex >= items.size()) {
            return false;
        }
        items.remove(selectedIndex);
        // Adjust selected index if needed
        if (selectedIndex >= items.size() && !items.isEmpty()) {
            selectedIndex = items.size() - 1;
        }
        return true;
    }

    public void printInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory kosong.");
        } else {
            System.out.println("Isi Inventory:");
            for (int i = 0; i < items.size(); i++) {
                String prefix = (i == selectedIndex) ? "> " : "  ";
                System.out.println(prefix + items.get(i).getName());
            }
        }
    }
}