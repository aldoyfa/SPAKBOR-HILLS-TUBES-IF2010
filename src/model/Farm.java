package model;

import java.time.LocalTime;

public class Farm {
    private LocalTime time;
    private int day;

    // Contoh Konstruktor
    public Farm(String name) {
        this.time = LocalTime.of(6, 0); // Misalnya mulai jam 6 pagi
        this.day = 1;
    }

    public void advanceTime(LocalTime timeCost) {
        int totalMinutes = timeCost.getHour() * 60 + timeCost.getMinute();
        this.time = this.time.plusMinutes(totalMinutes);
        if (this.time.getHour() >= 24) {
            this.day += this.time.getHour() / 24;
            this.time = this.time.minusHours(24 * (this.time.getHour() / 24));
        }
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }
}
