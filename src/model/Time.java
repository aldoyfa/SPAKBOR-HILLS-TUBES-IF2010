package model;

import java.util.Random;

public class Time {
    private int minute = 0;
    private int hour = 6;
    private int dayActual = 1;
    private int daySeason = 1;
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

        if (hour == 24) {
            hour = 0;
            nextDay(); // langsung ganti hari, musim, cuaca
        }
    }

    private void nextDay() {
        dayActual++;
        daySeason++;

        // Jika sudah 10 hari, ganti season
        if (daySeason > 10) {
            seasonIndex = (seasonIndex + 1) % seasons.length;
            rainyDaysThisSeason = 0; // reset
            daySeason = 1;
        }

        generateWeather();
    }

    private void generateWeather() {
        // minimal 2 rainy days per season
        int remainingDays = 11 - daySeason; // sisa hari di season ini

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

    public void resetToMorning() {
        hour = 6;
        minute = 0;
    }    

    public String getCurrentTime() {
        return String.format(
            "Hari ke-%d | Musim: %s | Jam: %02d:%02d | Cuaca: %s",
            dayActual, seasons[seasonIndex], hour, minute, weather
        );
    }

    public String getSeason() {
        return seasons[seasonIndex];
    }

    public String getWeather() {
        return weather;
    }

    public int getDay() {
        return dayActual;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

}