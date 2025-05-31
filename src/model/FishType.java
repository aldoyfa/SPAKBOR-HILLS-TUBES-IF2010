package model;

public enum FishType {
    COMMON("Common Fish"),
    REGULAR("Regular Fish"),
    LEGENDARY("Legendary Fish");

    private final String displayName;

    FishType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}