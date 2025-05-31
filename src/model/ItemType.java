package model;

public enum ItemType {
    SEED("Seed"),           // Benih untuk ditanam
    CROPS("Crops"),           // Hasil panen
    FISH("Fish"),           // Hasil memancing
    FOOD("Food"),           // Makanan olahan
    EQUIPMENT("Equipment"),           // Alat kerja
    MISC("Misc"), ;  // item acak selain 5 diatas
        
    private final String displayName;
    
    ItemType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}