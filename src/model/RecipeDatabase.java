package model;

import java.util.List;
import java.util.ArrayList;

public class RecipeDatabase {
    public static List<Recipe> allRecipes = new ArrayList<>();

    static {
        allRecipes.add(new Recipe(
            "Fish n’ Chips",
            List.of(
                new Item("Any Fish", 2, ItemType.FISH),
                new Item("Wheat", 1, ItemType.CROPS),
                new Item("Potato", 1, ItemType.CROPS)
            ),
            new Item("Fish n’ Chips", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Baguette",
            List.of(new Item("Wheat", 3, ItemType.CROPS)),
            new Item("Baguette", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Sashimi",
            List.of(new Item("Salmon", 3, ItemType.FISH)),
            new Item("Sashimi", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Fugu",
            List.of(new Item("Pufferfish", 1, ItemType.FISH)),
            new Item("Fugu", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Wine",
            List.of(new Item("Grape", 2, ItemType.CROPS)),
            new Item("Wine", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Pumpkin Pie",
            List.of(
                new Item("Egg", 1, ItemType.CROPS),
                new Item("Wheat", 1, ItemType.CROPS),
                new Item("Pumpkin", 1, ItemType.CROPS)
            ),
            new Item("Pumpkin Pie", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Veggie Soup",
            List.of(
                new Item("Cauliflower", 1, ItemType.CROPS),
                new Item("Parsnip", 1, ItemType.CROPS),
                new Item("Potato", 1, ItemType.CROPS),
                new Item("Tomato", 1, ItemType.CROPS)
            ),
            new Item("Veggie Soup", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Fish Stew",
            List.of(new Item("Any Fish", 2, ItemType.FISH)),
            new Item("Fish Stew", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Spakbor Salad",
            List.of(
                new Item("Melon", 1, ItemType.CROPS),
                new Item("Cranberry", 1, ItemType.CROPS),
                new Item("Blueberry", 1, ItemType.CROPS),
                new Item("Tomato", 1, ItemType.CROPS)
            ),
            new Item("Spakbor Salad", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "Fish Sandwich",
            List.of(
                new Item("Any Fish", 1, ItemType.FISH),
                new Item("Wheat", 2, ItemType.CROPS),
                new Item("Tomato", 1, ItemType.CROPS),
                new Item("Hot Pepper", 1, ItemType.CROPS)
            ),
            new Item("Fish Sandwich", 1, ItemType.FOOD),
            1, "Firewood"
        ));

        allRecipes.add(new Recipe(
            "The Legends of Spakbor",
            List.of(
                new Item("Legend", 1, ItemType.FISH),
                new Item("Potato", 2, ItemType.CROPS),
                new Item("Parsnip", 1, ItemType.CROPS),
                new Item("Tomato", 1, ItemType.CROPS),
                new Item("Eggplant", 1, ItemType.CROPS)
            ),
            new Item("The Legends of Spakbor", 1, ItemType.FOOD),
            2, "Coal" // Sesuai spesifikasi: 1 Coal = 2 masakan
        ));
    }

    public static List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public static Recipe getRecipeByName(String name) {
        for (Recipe r : allRecipes) {
            if (r.getName().equalsIgnoreCase(name)) return r;
        }
        return null;
    }
}