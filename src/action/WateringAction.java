package action;

import main.GamePanel;

public class WateringAction implements Action {
    private GamePanel gp;

    public WateringAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Cek energy player (5 energi sesuai spek)
        if (gp.player.getEnergy() >= 5) {
            // Cek apakah player memiliki Watering Can
            if (!gp.player.hasItem("Watering Can")) {
                gp.ui.currentDialogue = "You need a Watering Can to water plants!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // PERBAIKAN: Gunakan directional watering
            if (canWaterTargetTile()) {
                waterTargetTile();
                
                // Update energy dan waktu sesuai spek
                gp.player.setEnergy(-5);
                
                // Tambah waktu 5 menit (1 tick = 5 menit)
                gp.farmMap.time.tick();
                
                gp.ui.currentDialogue = "You watered the plant!\nIt will help the crop grow better.";
                gp.gameState = gp.dialogueState;
            } else {
                gp.ui.currentDialogue = "There's no planted crop in that direction!\nFace towards planted soil (green tiles) and try again.";
                gp.gameState = gp.dialogueState;
            }
        } else {
            gp.ui.currentDialogue = "You don't have enough energy to water! (Need 5 energy)";
            gp.gameState = gp.dialogueState;
        }
    }
    
    // PERBAIKAN: Gunakan directional detection
    private boolean canWaterTargetTile() {
        int[] targetTile = getTargetTileByDirection();
        if (targetTile == null) return false;
        
        int checkCol = targetTile[0];
        int checkRow = targetTile[1];
        
        // Cek bounds
        if (checkCol < 0 || checkCol >= gp.maxWorldCol || 
            checkRow < 0 || checkRow >= gp.maxWorldRow) {
            return false;
        }
        
        // Cek apakah tile adalah planted (5)
        int tileNum = gp.farmMap.mapTileNum[checkCol][checkRow];
        return tileNum == 5; // 5 = planted
    }
    
    // PERBAIKAN: Water tile di direction player
    private void waterTargetTile() {
        int[] targetTile = getTargetTileByDirection();
        if (targetTile != null) {
            int tileCol = targetTile[0];
            int tileRow = targetTile[1];
            
            // Update watering state jika diperlukan
            // Untuk sekarang memberikan feedback visual
            System.out.println("Plant at (" + tileCol + ", " + tileRow + ") has been watered!");
            
            // Optional: Track watering state
            if (gp.farmMap.tile[5] != null) {
                // Future enhancement: gp.farmMap.setTileWatered(tileCol, tileRow, true);
            }
        }
    }
    
    // METHOD UTAMA: Dapatkan tile berdasarkan direction player
    private int[] getTargetTileByDirection() {
        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;
        
        // Dapatkan tile di depan player berdasarkan direction
        switch (gp.player.direction) {
            case "up":
                return new int[]{playerCol, playerRow - 1};
            case "down":
                return new int[]{playerCol, playerRow + 1};
            case "left":
                return new int[]{playerCol - 1, playerRow};
            case "right":
                return new int[]{playerCol + 1, playerRow};
            default:
                return null;
        }
    }
}