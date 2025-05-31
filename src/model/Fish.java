package model;

import java.util.*;

public class Fish extends Item {
    private FishType type;

    public Fish(String name, FishType type) {
        super(name);
        this.type = type;
    }

    public FishType getType() {
        return type;
    }

    public static Fish randomFish(String location, String season, String weather) {
        List<Fish> pool = new ArrayList<>();

        if (location.equalsIgnoreCase("Pond")) {
            pool.add(new Fish("Bullhead", FishType.COMMON));
            pool.add(new Fish("Carp", FishType.COMMON));
            if (season.equalsIgnoreCase("Spring") && weather.equalsIgnoreCase("Sunny")) {
                pool.add(new Fish("Sunfish", FishType.REGULAR));
            }
        } else if (location.equalsIgnoreCase("Ocean")) {
            pool.add(new Fish("Halibut", FishType.REGULAR));
            pool.add(new Fish("Octopus", FishType.REGULAR));
            pool.add(new Fish("Crimsonfish", FishType.LEGENDARY));
            if (season.equalsIgnoreCase("Winter")) {
                pool.add(new Fish("Glacierfish", FishType.LEGENDARY));
            }
        }

        if (pool.isEmpty()) {
            return new Fish("Unknown Fish", FishType.COMMON);
        }

        Random rand = new Random();
        return pool.get(rand.nextInt(pool.size()));
    }
}
