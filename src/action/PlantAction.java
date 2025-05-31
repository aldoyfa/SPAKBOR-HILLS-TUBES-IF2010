package action;

import main.GamePanel;
import model.Item;
import model.ItemType;
import java.util.List;

public class PlantAction implements Action {
    private GamePanel gp;

    public PlantAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // PERBAIKAN 1: Cek energy sesuai spek (5, bukan 10)
        if (gp.player.getEnergy() >= 5) {
            // PERBAIKAN 2: Cek apakah player berada di tile yang sudah tilled
            if (!isPlayerOnTilledSoil()) {
                gp.ui.currentDialogue = "You need to be on tilled soil to plant seeds!\nFind tilled land (brown soil) first.";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // Filter item SEED dari inventory
            List<Item> seedItems = gp.player.getInventory().getItemsByType(ItemType.SEED);
            
            if (seedItems.isEmpty()) {
                gp.ui.currentDialogue = "You don't have any seeds to plant!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // EXISTING CODE: Langsung ke plantInventoryState
            gp.ui.filteredItems.clear();
            gp.ui.filteredItems.addAll(seedItems);
            gp.ui.filteredInventorySelectionIndex = 0;
            gp.gameState = gp.plantInventoryState;
        } else {
            gp.ui.currentDialogue = "You don't have enough energy to plant! (Need 5 energy)";
            gp.gameState = gp.dialogueState;
        }
    }
    
    // TAMBAHAN: Method untuk cek apakah player di tile yang sudah tilled
    private boolean isPlayerOnTilledSoil() {
        // Convert player position to map coordinates (SESUAI STRUKTUR ANDA)
        int tileCol = gp.player.worldX / gp.tileSize;
        int tileRow = gp.player.worldY / gp.tileSize;
        
        // Cek apakah dalam bounds map
        if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
            return false;
        }
        
        // Cek tile number - berdasarkan TileRenderer setup: 2 = tilled
        int tileNum = gp.farmMap.mapTileNum[tileCol][tileRow];
        return tileNum == 2; // 2 = tilled berdasarkan getTileImage()
    }
    
    // PERBAIKAN: Update execute plant logic sesuai spek dan struktur existing
    public static void executePlantLogic(GamePanel gp) {
        if (gp.ui.filteredItems != null && !gp.ui.filteredItems.isEmpty() && 
            gp.ui.filteredInventorySelectionIndex < gp.ui.filteredItems.size()) {
            
            Item selectedSeed = gp.ui.filteredItems.get(gp.ui.filteredInventorySelectionIndex);
            
            // PERBAIKAN 3: Plant seed di tile saat ini
            int tileCol = gp.player.worldX / gp.tileSize;
            int tileRow = gp.player.worldY / gp.tileSize;
            
            // Validasi sekali lagi untuk safety
            if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
                gp.ui.currentDialogue = "Cannot plant here - out of bounds!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            int currentTileNum = gp.farmMap.mapTileNum[tileCol][tileRow];
            if (currentTileNum != 2) { // 2 = tilled
                gp.ui.currentDialogue = "This soil is not tilled! Use your Hoe first.";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // PERBAIKAN 4: Update tile ke planted state (SESUAI TILEREADER SETUP)
            gp.farmMap.mapTileNum[tileCol][tileRow] = 5; // 5 = planted berdasarkan getTileImage()
            
            // Logic untuk menanam
            String reaction = "You planted " + selectedSeed.getName() + "! ";
            
            // PERBAIKAN 5: Crop result dengan growth time yang lebih detail
            String cropResult = "";
            switch (selectedSeed.getName().toLowerCase()) {
                case "parsnip seeds":
                    cropResult = "\nParsnip will grow in 4 days.";
                    break;
                case "cauliflower seeds":
                    cropResult = "\nCauliflower will grow in 12 days.";
                    break;
                case "potato seeds":
                    cropResult = "Potato will grow in 6 days.";
                    break;
                case "turnip seeds":
                    cropResult = "Turnip will grow in 4 days.";
                    break;
                default:
                    cropResult = "\nSeeds have been planted.";
            }
            
            reaction += cropResult;
            
            // Hapus 1 quantity dari seeds
            gp.player.getInventory().removeItem(selectedSeed.getBaseName(), 1);
            
            // PERBAIKAN 6: Update energy cost sesuai spek (5, bukan 10)
            gp.player.setEnergy(-5);
            
            // PERBAIKAN 7: Tambah waktu game 5 menit (SESUAI STRUKTUR TIME EXISTING)
            // Berdasarkan GamePanel.java, time system ada di gp.farmMap.time
            for (int i = 0; i < 5; i++) {
                gp.farmMap.time.tick(); // Tambah 5 tick = 5 menit
            }
            
            // Clear filtered items
            gp.ui.filteredItems.clear();
            
            // Set dialogue dan pindah ke dialogue state
            gp.ui.currentDialogue = reaction;
            gp.gameState = gp.dialogueState;
        }
    }
}