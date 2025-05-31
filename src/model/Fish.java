package model;

import java.util.List;

public class Fish {
    private String name;
    private FishType type;
    private List<String> seasons;
    private List<String> weathers;
    private List<String> locations;
    private List<TimeRange> timeRanges;

    public Fish(String name, FishType type, List<String> seasons, List<TimeRange> timeRanges, List<String> weathers, List<String> locations) {
        this.name = name;
        this.type = type;
        this.seasons = seasons;
        this.timeRanges = timeRanges;
        this.weathers = weathers;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public FishType getType() {
        return type;
    }

    public int getMaxNumber() {
        switch (type) {
            case COMMON: return 10;
            case REGULAR: return 100;
            case LEGENDARY: return 500;
            default: return 10;
        }
    }

    public int getMaxAttempts() {
        return (type == FishType.LEGENDARY) ? 7 : 10;
    }

    public boolean isAvailable(String season, int hour, String weather, String location) {
        boolean seasonOk = seasons.contains("Any") || seasons.contains(season);
        boolean weatherOk = weathers.contains("Any") || weathers.contains(weather);
        boolean locationOk = locations.contains(location);

        boolean timeOk = false;
        for (TimeRange range : timeRanges) {
            if (range.contains(hour)) {
                timeOk = true;
                break;
            }
        }
        return seasonOk && timeOk && weatherOk && locationOk;
    }
}