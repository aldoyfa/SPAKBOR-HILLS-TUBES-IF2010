package model;

import java.util.*;

/**
 * Simple database untuk semua item dalam game
 * Menggunakan string arrays per ItemType
 */
public class ItemDatabase {
    
    // SEED items - Berdasarkan PlantAction existing
    public static final String[] SEEDS = {
        "Parsnip Seeds", "Cauliflower Seeds", "Potato Seeds", "Turnip Seeds",
        "Tomato Seeds", "Blueberry Seeds", "Corn Seeds", "Pumpkin Seeds",
        "Beet Seeds", "Wheat Seeds"
    };
    
    // CROPS items - Hasil harvest dari seeds
    public static final String[] CROPS = {
        "Parsnip", "Cauliflower", "Potato", "Turnip", "Tomato",
        "Blueberry", "Corn", "Pumpkin", "Beet", "Wheat"
    };
    
    // FISH items - Hasil memancing
    public static final String[] FISH = {
        "Sardine", "Tuna", "Salmon", "Carp", "Bass",
        "Catfish", "Eel", "Lobster", "Crab", "Shrimp"
    };
    
    // FOOD items - Berdasarkan EatAction existing + tambahan
    public static final String[] FOOD = {
        "Baguette", "Fish n' Chips", "Wine", "Salad", "Soup",
        "Pizza", "Cake", "Coffee", "Sandwich", "Pasta"
    };
    
    // EQUIPMENT items - Berdasarkan Player.setDefaultValues existing + tambahan
    public static final String[] EQUIPMENT = {
        "Hoe", "Watering Can", "Pickaxe", "Fishing Rod", "Proposal Ring",
        "Axe", "Sword", "Scythe", "Hammer", "Shovel"
    };
    
    // MISC items - Materials dan miscellaneous
    public static final String[] MISC = {
        "Wood", "Stone", "Clay", "Coal", "Iron Ore",
        "Gold Ore", "Gem", "Battery", "Fiber", "Sap"
    };
    
    // Method untuk check apakah item exists
    public static boolean itemExists(String itemName) {
        return getItemType(itemName) != null;
    }
    
    // Method untuk get ItemType dari nama item
    public static ItemType getItemType(String itemName) {
        String normalizedName = itemName.trim();
        
        if (Arrays.asList(SEEDS).contains(normalizedName)) {
            return ItemType.SEED;
        }
        if (Arrays.asList(CROPS).contains(normalizedName)) {
            return ItemType.CROPS;
        }
        if (Arrays.asList(FISH).contains(normalizedName)) {
            return ItemType.FISH;
        }
        if (Arrays.asList(FOOD).contains(normalizedName)) {
            return ItemType.FOOD;
        }
        if (Arrays.asList(EQUIPMENT).contains(normalizedName)) {
            return ItemType.EQUIPMENT;
        }
        if (Arrays.asList(MISC).contains(normalizedName)) {
            return ItemType.MISC;
        }
        
        return null; // Item tidak ditemukan
    }
    
    // Method untuk get random item by type
    public static String getRandomItemByType(ItemType type) {
        String[] items = getItemsByType(type);
        if (items.length > 0) {
            Random random = new Random();
            return items[random.nextInt(items.length)];
        }
        return null;
    }
    
    // Method untuk get all items by type
    public static String[] getItemsByType(ItemType type) {
        switch (type) {
            case SEED: return SEEDS.clone();
            case CROPS: return CROPS.clone();
            case FISH: return FISH.clone();
            case FOOD: return FOOD.clone();
            case EQUIPMENT: return EQUIPMENT.clone();
            case MISC: return MISC.clone();
            default: return new String[0];
        }
    }
}