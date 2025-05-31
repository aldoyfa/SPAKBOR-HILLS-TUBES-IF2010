package model;

public class Item {
    private String name;
    private int quantity;
    private ItemType type;

    // Constructor dengan database lookup
    public Item(String name, int quantity, ItemType type) {
        // Cek apakah item ada di database dan type cocok
        ItemType dbType = ItemDatabase.getItemType(name);
        if (dbType != null && dbType == type) {
            // Item valid di database
            this.name = name;
            this.type = dbType;
        } else if (dbType != null) {
            // Item ada di database tapi type salah, gunakan type dari database
            this.name = name;
            this.type = dbType;
            System.out.println("Warning: Item '" + name + "' type corrected to " + dbType.getDisplayName());
        } else {
            // Item tidak ada di database, gunakan input
            this.name = name;
            this.type = type;
            System.out.println("Warning: Item '" + name + "' not found in database!");
        }
        
        this.quantity = quantity;
    }

    // EXISTING methods tetap sama
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

    public String getSimpleDisplayName() {
        return (quantity > 1) ? name + " x" + quantity : name;
    }

    public String getDisplayName() {
        String displayName = (quantity > 1) ? name + " x" + quantity : name;
        return displayName + " [" + type.getDisplayName() + "]";
    }

    public String getBaseName() {
        return name;
    }

    public boolean isStackable() {
        return type != ItemType.EQUIPMENT && type != ItemType.MISC;
    }
    
    // TAMBAHAN: Static helper methods (menggantikan ItemUtils)
    public static boolean exists(String itemName) {
        return ItemDatabase.itemExists(itemName);
    }
    
    public static Item create(String itemName, int quantity) {
        ItemType type = ItemDatabase.getItemType(itemName);
        if (type != null) {
            return new Item(itemName, quantity, type);
        }
        return null;
    }
    
    public static String getRandomByType(ItemType type) {
        return ItemDatabase.getRandomItemByType(type);
    }
}
