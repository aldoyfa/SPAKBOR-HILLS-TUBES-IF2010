package model;

import java.util.Random;

public class Time {
    private int minute = 0;
    private int hour = 6;
    private int day = 1;
    private int seasonIndex = 0;
    private String[] seasons = {"Spring", "Summer", "Fall", "Winter"};
    private String weather = "Sunny";

    private int rainyDaysThisSeason = 0;
    private Random rand = new Random();

    
    public void tick() {
        minute += 5; // 1 tick = 5 menit

        if (minute >= 60) {
            minute = 0;
            hour++;
        }

        if (hour >= 24) {
            hour = 6; // waktu pagi
            minute = 0;
            nextDay();
        }
    }

    private void nextDay() {
        day++;

        // Jika sudah 10 hari, ganti season
        if (day > 10) {
            day = 1;
            seasonIndex = (seasonIndex + 1) % seasons.length;
            rainyDaysThisSeason = 0; // reset
        }

        generateWeather();
    }

    private void generateWeather() {
        // minimal 2 rainy days per season
        int remainingDays = 11 - day; // sisa hari di season ini

        if (rainyDaysThisSeason < 2 && remainingDays <= (2 - rainyDaysThisSeason)) {
            weather = "Rainy";
            rainyDaysThisSeason++;
            return;
        }

        boolean rainToday = rand.nextInt(100) < 25; // 25% chance rain
        if (rainToday) {
            weather = "Rainy";
            rainyDaysThisSeason++;
        } else {
            weather = "Sunny";
        }
    }

    public String getCurrentTime() {
        return String.format(
            "Hari ke-%d | Musim: %s | Jam: %02d:%02d | Cuaca: %s",
            day, seasons[seasonIndex], hour, minute, weather
        );
    }

    public String getSeason() {
        return seasons[seasonIndex];
    }

    public String getWeather() {
        return weather;
    }
}

