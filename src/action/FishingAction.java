package action;

import entity.Player;
import java.util.Random;
import java.util.Scanner;
import model.Fish;
import model.FishType;
import model.Time;

public class FishingAction implements Action {
    private String location; // misal: "Pond", "Ocean"
    private Time time;
    private Random random = new Random();

    public FishingAction(String location, Time time) {
        this.location = location;
        this.time = time;
    }

    @Override
    public void execute(Player player) {
        if (player.getEnergy() < 5) {
            System.out.println("Energi tidak cukup untuk memancing.");
            return;
        }

        player.reduceEnergy(5);
        // Add 15 minutes by calling tick() 3 times (5 minutes per tick)
        for (int i = 0; i < 3; i++) {
            time.tick();
        }

        // RNG menentukan ikan yang muncul, now considering season and weather
        Fish caughtFish = Fish.randomFish(location, time.getSeason(), time.getWeather());
        FishType type = caughtFish.getType();

        int maxTries = type == FishType.LEGENDARY ? 7 : 10;
        int range = type == FishType.COMMON ? 10 : (type == FishType.REGULAR ? 100 : 500);
        int target = random.nextInt(range) + 1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Memancing " + caughtFish.getName() + " (" + type + ") - Tebak angka dari 1 sampai " + range);

        boolean success = false;
        for (int i = 1; i <= maxTries; i++) {
            System.out.print("Tebakan ke-" + i + ": ");
            int guess = scanner.nextInt();
            if (guess == target) {
                success = true;
                break;
            }
        }

        if (success) {
            player.getInventory().addItem(caughtFish);
            System.out.println("Berhasil menangkap " + caughtFish.getName() + "!");
        } else {
            System.out.println("Gagal menangkap ikan.");
        }
    }
}
