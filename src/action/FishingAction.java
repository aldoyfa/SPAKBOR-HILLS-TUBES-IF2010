package action;
import java.time.LocalTime;
import java.util.*;

import model.Action;
import model.Farm;

public class FishingAction implements Action {
    private final int energyCost = 5;
    private final LocalTime timeCost = LocalTime.of(0, 15);
    private final Random rand = new Random();

    @Override
    public void execute(Player player, Farm farm, String args) {
        Location location = player.getLocation(); // misalnya Pond

        if (player.getEnergy() < energyCost) {
            System.out.println("Energi tidak cukup.");
            return;
        }

        // Random jenis ikan (sementara kita asumsikan selalu dapat Halibut)
        Fish fish = new Fish("Halibut"); // bisa buat sistem registry nanti

        int correctNumber = rand.nextInt(100) + 1; // tebak 1-100
        int attempts = 10;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tebak angka dari 1-100! (maks 10 percobaan)");
        while (attempts-- > 0) {
            System.out.print("Tebakan: ");
            int guess = scanner.nextInt();

            if (guess == correctNumber) {
                System.out.println("Selamat! Kamu mendapatkan ikan " + fish.getName());
                player.getInventory().addItem(fish, 1);
                break;
            } else if (guess < correctNumber) {
                System.out.println("Terlalu kecil.");
            } else {
                System.out.println("Terlalu besar.");
            }

            if (attempts == 0) {
                System.out.println("Sayang sekali, kamu tidak mendapat ikan.");
            }
        }

        player.deductEnergy(energyCost);
        farm.advanceTime(timeCost);
    }

    @Override
    public boolean isExecutable(Player player) {
        return player.getEnergy() >= energyCost;
    }
}
