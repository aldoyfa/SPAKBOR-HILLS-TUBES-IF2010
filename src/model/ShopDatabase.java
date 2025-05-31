package model;

import java.util.*;

import main.GamePanel;

public class ShopDatabase {
    
    // Shop items dengan buy price (TANPA FURNITURE)
    private static final Map<String, Integer> SHOP_PRICES = new HashMap<String, Integer>() {{
        // SEEDS
        put("Parsnip Seeds", 20);
        put("Cauliflower Seeds", 80);
        put("Potato Seeds", 50);
        put("Wheat Seeds", 60);
        put("Blueberry Seeds", 80);
        put("Tomato Seeds", 50);
        put("Hot Pepper Seeds", 40);
        put("Melon Seeds", 80);
        put("Cranberry Seeds", 100);
        put("Pumpkin Seeds", 150);
        put("Grape Seeds", 60);
        
        // CROPS (untuk referensi sell price)
        put("Parsnip", 35);
        put("Cauliflower", 200);
        put("Potato", 150);
        put("Wheat", 80);
        put("Blueberry", 50);
        put("Tomato", 30);
        put("Hot Pepper", 150);
        put("Melon", 40);
        put("Cranberry", 90);
        put("Pumpkin", 60);
        put("Grape", 40);
        
        // FOOD
        put("Fish n' Chips", 100);
        put("Baguette", 10);
        put("Sashimi", 150);
        put("Fugu", 135);
        put("Wine", 100);
        put("Pumpkin Pie", 80);
        put("Veggie Soup", 300);
        put("Fish Stew", 275);
        put("Spakbor Salad", 135);
        put("Fish Sandwich", 100);
        put("The Legends of Spakbor", 90);
        put("Cooked Pig's Head", 120);
        
        // MISC
        put("Egg", 100);
        put("Firewood", 10);
        put("Coal", 100);
        put("Proposal Ring", 2000);
    }};
    
    // Items yang bisa dibeli di shop
    private static final Set<String> BUYABLE_ITEMS = new HashSet<>(Arrays.asList(
        // Seeds - semua bisa dibeli
        "Parsnip Seeds", "Cauliflower Seeds", "Potato Seeds", "Wheat Seeds",
        "Blueberry Seeds", "Tomato Seeds", "Hot Pepper Seeds", "Melon Seeds",
        "Cranberry Seeds", "Pumpkin Seeds", "Grape Seeds",
        
        // Food - bisa dibeli
        "Fish n' Chips", "Baguette", "Sashimi", "Fugu", "Wine",
        "Pumpkin Pie", "Veggie Soup", "Fish Stew", "Spakbor Salad",
        "Fish Sandwich", "The Legends of Spakbor", "Cooked Pig's Head",
        
        // Misc
        "Egg", "Firewood", "Coal", "Proposal Ring"
    ));
    
    public static boolean isItemBuyable(String itemName) {
        return BUYABLE_ITEMS.contains(itemName);
    }
    
    public static int getBuyPrice(String itemName) {
        return SHOP_PRICES.getOrDefault(itemName, 0);
    }
    
    public static int getSellPrice(String itemName) {
        // Sell price = setengah harga beli ATAU dari database
        int buyPrice = getBuyPrice(itemName);
        if (buyPrice > 0) {
            return buyPrice / 2; // Setengah harga beli
        }
        
        // Untuk crops yang tidak ada buy price, return fixed sell price
        return SHOP_PRICES.getOrDefault(itemName, 0) / 2;
    }
    
    // Method untuk buy categories
    public static List<String> getBuyableItemsByCategory(ItemType category) {
        List<String> items = new ArrayList<>();
        
        for (String item : BUYABLE_ITEMS) {
            ItemType itemType = ItemDatabase.getItemType(item);
            if (itemType == category) {
                items.add(item);
            }
        }
        
        return items;
    }
    
    // NEW: Method untuk sell categories (dari player inventory)
    public static List<String> getSellableItemsByCategory(GamePanel gp, ItemType category) {
        List<String> items = new ArrayList<>();
        
        // Get items dari player inventory berdasarkan category
        List<Item> playerItems = gp.player.getInventory().getItemsByType(category);
        
        for (Item item : playerItems) {
            // Hanya tambahkan jika item memiliki sell value dan belum ada di list
            if (getSellPrice(item.getBaseName()) > 0 && !items.contains(item.getBaseName())) {
                items.add(item.getBaseName());
            }
        }
        
        return items;
    }
    
    // NEW: Method untuk get sellable categories (hanya yang ada di inventory)
    public static List<ItemType> getSellableCategories(GamePanel gp) {
        List<ItemType> categories = new ArrayList<>();
        List<Item> allItems = gp.player.getInventory().getItems();
        
        // Check setiap category apakah ada items yang bisa dijual
        for (ItemType type : ItemType.values()) {
            boolean hasItems = allItems.stream()
                .anyMatch(item -> item.getType() == type && getSellPrice(item.getBaseName()) > 0);
            
            if (hasItems) {
                categories.add(type);
            }
        }
        
        return categories;
    }
}