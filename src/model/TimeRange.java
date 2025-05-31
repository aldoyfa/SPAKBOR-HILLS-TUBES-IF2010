package model;

public class TimeRange {
    private int startHour;
    private int endHour;

    public TimeRange(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public boolean contains(int hour) {
        if (startHour <= endHour) {
            return hour >= startHour && hour < endHour;
        } else {
            return hour >= startHour || hour < endHour;
        }
    }
}