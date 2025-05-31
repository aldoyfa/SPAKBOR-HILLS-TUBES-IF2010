package model;

import java.util.List;

public class Recipe {
    private String name;
    private List<Item> ingredients;
    private Item result;
    private int fuelRequired;
    private String fuelName;

    public Recipe(String name, List<Item> ingredients, Item result, int fuelRequired, String fuelName) {
        this.name = name;
        this.ingredients = ingredients;
        this.result = result;
        this.fuelRequired = fuelRequired;
        this.fuelName = fuelName;
    }

    public String getName() {
        return name;
    }

    public List<Item> getIngredients() {
        return ingredients;
    }

    public Item getResult() {
        return result;
    }

    public int getFuelRequired() {
        return fuelRequired;
    }

    public String getFuelName() {
        return fuelName;
    }

    public Item getFuelItem() {
        return new Item(fuelName, fuelRequired, ItemType.MISC);
    }
}