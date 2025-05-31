package action;

import main.GamePanel;

public class RecoverLandAction implements Action {
    private GamePanel gp;

    public RecoverLandAction(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        // Cek energy player (5 energi sesuai spek)
        if (gp.player.getEnergy() >= 5) {
            // Cek apakah player memiliki Pickaxe
            if (!gp.player.hasItem("Pickaxe")) {
                gp.ui.currentDialogue = "You need a Pickaxe to recover tilled land!";
                gp.gameState = gp.dialogueState;
                return;
            }
            
            // Cek apakah tile di posisi player bisa di-recover
            if (canRecoverCurrentTile()) {
                recoverCurrentTile();
                
                // Update energy dan waktu sesuai spek
                gp.player.setEnergy(-5);
                
                // Tambah waktu 5 menit (1 tick = 5 menit)
                gp.farmMap.time.tick();
                
                gp.ui.currentDialogue = "You recovered the tilled land!\nIt's now tillable again.";
                gp.gameState = gp.dialogueState;
            } else {
                gp.ui.currentDialogue = "There's no tilled land here to recover!\nLook for brown tilled soil.";
                gp.gameState = gp.dialogueState;
            }
        } else {
            gp.ui.currentDialogue = "You don't have enough energy to recover land! (Need 5 energy)";
            gp.gameState = gp.dialogueState;
        }
    }
    
    private boolean canRecoverCurrentTile() {
        int tileCol = gp.player.worldX / gp.tileSize;
        int tileRow = gp.player.worldY / gp.tileSize;
        
        // Cek bounds
        if (tileCol < 0 || tileCol >= gp.maxWorldCol || tileRow < 0 || tileRow >= gp.maxWorldRow) {
            return false;
        }
        
        // Cek tile number - berdasarkan TileRenderer: 2 = tilled
        int tileNum = gp.farmMap.mapTileNum[tileCol][tileRow];
        return tileNum == 2; // 2 = tilled berdasarkan getTileImage()
    }
    
    private void recoverCurrentTile() {
        int tileCol = gp.player.worldX / gp.tileSize;
        int tileRow = gp.player.worldY / gp.tileSize;
        
        // Update tile number ke tillable (0)
        gp.farmMap.mapTileNum[tileCol][tileRow] = 0; // 0 = tillable
    }
}