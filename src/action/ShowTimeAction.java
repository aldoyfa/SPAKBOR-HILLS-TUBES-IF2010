package action;

import main.GameModel;
import model.Farm;
import java.time.format.DateTimeFormatter;

public class ShowTimeAction implements Action {
    @Override
    public void execute(GameModel game) {
        Farm farm = game.getFarm();
        String season = farm.getSeason().toString();
        int day = farm.getDay();
        String time = farm.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("Musim: " + season);
        System.out.println("Hari ke-" + day);
        System.out.println("Waktu: " + time);
    }
}