package model;

import java.util.*;
import model.Fish;
import model.FishType;


public class FishDatabase {
    public static List<Fish> fishList = new ArrayList<>();

    static {
        // COMMON FISH
        fishList.add(new Fish("Bullhead", FishType.COMMON,
            List.of("Any"), List.of(new TimeRange(0, 24)), List.of("Any"),
            List.of("Mountain Lake")));

        fishList.add(new Fish("Carp", FishType.COMMON,
            List.of("Any"), List.of(new TimeRange(0, 24)), List.of("Any"),
            List.of("Pond", "Mountain Lake")));

        fishList.add(new Fish("Chub", FishType.COMMON,
            List.of("Any"), List.of(new TimeRange(0, 24)), List.of("Any"),
            List.of("Forest River", "Mountain Lake")));

        // REGULAR FISH
        fishList.add(new Fish("Largemouth Bass", FishType.REGULAR,
            List.of("Any"), List.of(new TimeRange(6, 18)), List.of("Any"),
            List.of("Mountain Lake")));

        fishList.add(new Fish("Rainbow Trout", FishType.REGULAR,
            List.of("Summer"), List.of(new TimeRange(6, 18)), List.of("Sunny"),
            List.of("Forest River", "Mountain Lake")));

        fishList.add(new Fish("Sturgeon", FishType.REGULAR,
            List.of("Summer", "Winter"), List.of(new TimeRange(6, 18)), List.of("Any"),
            List.of("Mountain Lake")));

        fishList.add(new Fish("Midnight Carp", FishType.REGULAR,
            List.of("Winter", "Fall"), List.of(new TimeRange(20, 2)), List.of("Any"),
            List.of("Mountain Lake", "Pond")));

        fishList.add(new Fish("Flounder", FishType.REGULAR,
            List.of("Spring", "Summer"), List.of(new TimeRange(6, 22)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Halibut", FishType.REGULAR,
            List.of("Any"), List.of(new TimeRange(6, 11), new TimeRange(19, 2)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Octopus", FishType.REGULAR,
            List.of("Summer"), List.of(new TimeRange(6, 22)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Pufferfish", FishType.REGULAR,
            List.of("Summer"), List.of(new TimeRange(0, 16)), List.of("Sunny"),
            List.of("Ocean")));

        fishList.add(new Fish("Sardine", FishType.REGULAR,
            List.of("Any"), List.of(new TimeRange(6, 18)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Super Cucumber", FishType.REGULAR,
            List.of("Summer", "Fall", "Winter"), List.of(new TimeRange(18, 2)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Catfish", FishType.REGULAR,
            List.of("Spring", "Summer", "Fall"), List.of(new TimeRange(6, 22)), List.of("Rainy"),
            List.of("Forest River", "Pond")));

        fishList.add(new Fish("Salmon", FishType.REGULAR,
            List.of("Fall"), List.of(new TimeRange(6, 18)), List.of("Any"),
            List.of("Forest River")));

        // LEGENDARY FISH
        fishList.add(new Fish("Angler", FishType.LEGENDARY,
            List.of("Fall"), List.of(new TimeRange(8, 20)), List.of("Any"),
            List.of("Pond")));

        fishList.add(new Fish("Crimsonfish", FishType.LEGENDARY,
            List.of("Summer"), List.of(new TimeRange(8, 20)), List.of("Any"),
            List.of("Ocean")));

        fishList.add(new Fish("Glacierfish", FishType.LEGENDARY,
            List.of("Winter"), List.of(new TimeRange(8, 20)), List.of("Any"),
            List.of("Forest River")));

        fishList.add(new Fish("Legend", FishType.LEGENDARY,
            List.of("Spring"), List.of(new TimeRange(8, 20)), List.of("Rainy"),
            List.of("Mountain Lake")));

    }

    public static List<Fish> getAvailableFishes(String season, int hour, String weather, String location) {
        List<Fish> available = new ArrayList<>();
        for (Fish fish : fishList) {
            if (fish.isAvailable(season, hour, weather, location)) {
                available.add(fish);
            }
        }
        return available;
    }
}