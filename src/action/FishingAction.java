package action;

import main.GamePanel;
import model.Fish;
import model.FishDatabase;

import java.util.List;
import java.util.Random;

public class FishingAction {

    public static void execute(GamePanel gp) {
        String season = gp.farmMap.time.getSeason();
        int hour = gp.farmMap.time.getHour();
        String weather = gp.farmMap.time.getWeather();
        String location = detectFishingLocation(gp);

        if (location == null) {
            gp.ui.currentDialogue = "You must be near a pond or water to fish!";
            gp.gameState = gp.dialogueState;
            return;
        }

        if (gp.player.getEnergy() < 5) {
            gp.ui.currentDialogue = "You don't have enough energy to fishing.";
            gp.gameState = gp.dialogueState;
            return;
        }

        List<Fish> options = FishDatabase.getAvailableFishes(season, hour, weather, location);
        if (options.isEmpty()) {
            gp.ui.currentDialogue = "No fish available at this time!";
            gp.gameState = gp.dialogueState;
            return;
        }

        Random rand = new Random();
        Fish selected = options.get(rand.nextInt(options.size()));

        gp.ui.selectedFish = selected;
        gp.ui.fishingAttempt = 1;
        gp.ui.fishingInput = "";
        gp.ui.fishingTarget = rand.nextInt(selected.getMaxNumber()) + 1;
        gp.ui.fishingMaxAttempts = selected.getMaxAttempts();
        gp.ui.fishingSuccess = false;

        gp.player.setEnergy(-5);
        gp.farmMap.time.addMinutes(15);
        gp.gameState = gp.fishingGuessState;
    }

    private static String detectFishingLocation(GamePanel gp) {
        int px = gp.player.worldX / gp.tileSize;
        int py = gp.player.worldY / gp.tileSize;

        // Cek di sekitar player (atas, bawah, kiri, kanan)
        int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dir) {
            int nx = px + d[0], ny = py + d[1];
            if (nx >= 0 && ny >= 0 && nx < gp.maxWorldCol && ny < gp.maxWorldRow) {
                int tileId = gp.farmMap.mapTileNum[nx][ny];
                if (tileId == 6) return "Ocean"; // Gunakan nama lokasi sesuai mapping
            }
        }

        // Cek apakah di sekitar pond
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null && gp.obj[i].name.equals("Pond")) {
                int pondX = gp.obj[i].worldX / gp.tileSize;
                int pondY = gp.obj[i].worldY / gp.tileSize;

                for (int dx = -1; dx <= 4; dx++) {
                    for (int dy = -1; dy <= 3; dy++) {
                        if ((dx == -1 || dx == 4 || dy == -1 || dy == 3)) {
                            int checkX = pondX + dx, checkY = pondY + dy;
                            if (checkX == px && checkY == py) return "Pond";
                        }
                    }
                }
            }
        }

        return null;
    }
}