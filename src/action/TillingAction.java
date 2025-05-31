package action;

import main.GamePanel;

public class TillingAction implements Action {
    private GamePanel gp;

    public TillingAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Cek energy player (5 energi sesuai spek)
        if (gp.player.getEnergy() >= 5) {
            // Cek apakah player memiliki Hoe
            if (!gp.player.hasItem("Hoe")) {
                gp.ui.currentDialogue = "You need a Hoe to till the land!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // Cek apakah tile di posisi player bisa di-till
            if (canTillCurrentTile()) {
                tillCurrentTile();
                
                // Update energy dan waktu sesuai spek
                gp.player.setEnergy(-5);
                
                // Tambah waktu 5 menit
                for (int i = 0; i < 5; i++) {
                    gp.farmMap.time.tick();
                }
                
                gp.ui.currentDialogue = "You tilled the soil!\nNow you can plant seeds here.";
                gp.gameState = gp.dialogueState;
            } else {
                gp.ui.currentDialogue = "This land cannot be tilled!\nMake sure you're on tillable land (grass).";
                gp.gameState = gp.dialogueState;
            }
        } else {
            gp.ui.currentDialogue = "You don't have enough energy to till! (Need 5 energy)";
            gp.gameState = gp.dialogueState;
        }
    }
    
    private boolean canTillCurrentTile() {
        int tileCol = gp.player.worldX / gp.tileSize;
        int tileRow = gp.player.worldY / gp.tileSize;
        
        // Cek bounds
        if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
            return false;
        }
        
        // Cek tile number - berdasarkan TileRenderer: 0 = tillable
        int tileNum = gp.farmMap.mapTileNum[tileCol][tileRow];
        return tileNum == 0; // 0 = tillable berdasarkan getTileImage()
    }
    
    private void tillCurrentTile() {
        int tileCol = gp.player.worldX / gp.tileSize;
        int tileRow = gp.player.worldY / gp.tileSize;
        
        // Update tile number ke tilled (2)
        gp.farmMap.mapTileNum[tileCol][tileRow] = 2; // 2 = tilled
    }
}