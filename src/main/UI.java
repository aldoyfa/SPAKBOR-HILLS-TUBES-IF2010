package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.FontFormatException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.Item;
import objects.NPC;

import java.awt.Font;
import java.awt.Color;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage goldImage, energyImage, mainMenuImage, newGameImage, loadGameImage, creditsImage, exitImage;
    public Rectangle newGameRect, loadGameRect, creditsRect, exitRect;
    Font maruMonica;
    public String currentDialogue = "";
    public NPC currentNPC;
    public int hoveredButton = -1;
    public String playerName = "";
    public String farmName = "";
    public int chooseIndex = 0; 
    public boolean enteringPlayerName = true;
    public boolean enteringFarmName = false;
    public boolean selectingGender = false;
    public String[] npcOptions = {"Chat", "Gift", "Propose", "Marry"};
    public int npcOptionIndex = 0;
    public String[] tileOptions = {"Tiling", "Recover", "Plant", "Water", "Harvest"};
    public int tileOptionIndex = 0;
    public int inventorySelectionIndex = 0;
    public int filteredInventorySelectionIndex = 0;
    public List<Item> filteredItems = new ArrayList<>();
    public int plantingCol, plantingRow;
    public String selectedItemDisplay = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);
        
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // NEW GAME STATE
        if (gp.gameState == gp.newGameState) {
            drawInputScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawGoldUI();
            drawEnergy();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // NPC INTERFACE STATE
        if (gp.gameState == gp.NPCInterfaceState) {
            drawNPCInterfaceScreen();
        }

        // INVENTORY SELECTION STATE
        if (gp.gameState == gp.inventorySelectionState) {
            drawInventorySelectionScreen();
        }

        // INVENTORY STATE 
        if (gp.gameState == gp.inventoryState) {
            drawInventoryScreen();
        }

        // EAT INVENTORY STATE
        if (gp.gameState == gp.eatInventoryState) {
            drawEatInventoryScreen();
        }

        // PLANT INVENTORY STATE  
        if (gp.gameState == gp.tileActionState) {
            drawTileActionScreen();
        }
        
        // PLANT INVENTORY STATE  
        if (gp.gameState == gp.plantInventoryState) {
            drawPlantInventoryScreen();
        }

        // WORLD MAP STATE
        if (gp.gameState == gp.worldMapState) {
            drawWorldMapScreen();
        }
    }

    public void drawTitleScreen() {
        try {
            mainMenuImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/Main Menu.png"));
            newGameImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/newgame.png"));
            loadGameImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/loadgame.png"));
            creditsImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/credits.png"));
            exitImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/exit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(mainMenuImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        int x = gp.tileSize * 2;
        int y = gp.screenHeight / 2 + gp.tileSize * 2;
        g2.drawImage(newGameImage, x, y,null);
        g2.drawImage(loadGameImage, x+256, y, null);
        g2.drawImage(creditsImage, x+512, y, null);
        g2.drawImage(exitImage, x+768, y, null);

        int width = newGameImage.getWidth();
        int height = newGameImage.getHeight();

        newGameRect = new Rectangle(x, y, width, height);
        loadGameRect = new Rectangle(x + 256, y, width, height);
        creditsRect = new Rectangle(x + 512, y, width, height);
        exitRect = new Rectangle(x + 768, y, width, height);

        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(6F));
        if (hoveredButton == 0) {
            g2.drawRect(x, y, width, height);
        } else if (hoveredButton == 1) {
            g2.drawRect(x + 256, y, width, height);
        } else if (hoveredButton == 2) {
            g2.drawRect(x + 512, y, width, height);
        } else if (hoveredButton == 3) {
            g2.drawRect(x + 768, y, width, height);
        }
    }

    public void drawInputScreen() {

         // DIALOGUE BOX
        drawDialogueBox();
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
                
        // DIALOGUE TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        x += gp.tileSize;
        y += gp.tileSize;

        if (enteringPlayerName) {
            g2.drawString("Enter your name:", x, y);
            g2.drawString(playerName + "_", x, y + 65);
        } else if (enteringFarmName) {
            g2.drawString("Enter your farm name:", x, y);
            g2.drawString(farmName + "_", x, y + 65);
        } else if (selectingGender) {
            g2.drawString("Select Gender: (use left and right arrows)", x, y);
            String genderStr = (chooseIndex == 0) ? "Male" : "Female";
            g2.setColor(Color.YELLOW);
            g2.drawString(genderStr, x, y + 65);
            g2.setColor(Color.WHITE);
            g2.drawString("Press ENTER to confirm", x, y + 130);
        }
    }

    public void drawNPCInterfaceScreen() {
        drawDialogueBox();
        int x = gp.tileSize * 3;
        int y = gp.tileSize * 2 ;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        g2.setColor(Color.WHITE);
        g2.drawString("Select Action (←/→ to move, ENTER to confirm):", x, y);

        int spacing = gp.tileSize * 3;

        for (int i = 0; i < npcOptions.length; i++) {
            if (i == npcOptionIndex) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(npcOptions[i], x + (i * spacing), y + gp.tileSize * 2);
        }
    }

     public void drawTileActionScreen() {
        drawDialogueBox();
        int x = gp.tileSize * 3;
        int y = gp.tileSize * 2 ;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        g2.setColor(Color.WHITE);
        g2.drawString("Select Action (←/→ to move, ENTER to confirm):", x, y);

        int spacing = gp.tileSize * 3;

        for (int i = 0; i < tileOptions.length; i++) {
            if (i == tileOptionIndex) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(tileOptions[i], x + (i * spacing), y + gp.tileSize * 2);
        }
    }


    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight * 2 / 8;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // DIALOGUE BOX
        drawDialogueBox();

        // DIALOGUE TEXT
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 55;
        }

        // PERBAIKAN: HANYA TAMPILKAN NPC INFO JIKA currentNPC TIDAK NULL
        if (currentNPC != null) {
            // NPC NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString(currentNPC.getName(), x, 270);

            // Heart Points
            try {
                // ENERGY IMAGE (digunakan sebagai heart icon)
                energyImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/energy.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.drawImage(energyImage, x + gp.tileSize*3, 235, goldImage.getWidth()*4/3, goldImage.getHeight()*4/3, null);
            g2.drawString("Heart Points: " + currentNPC.getHeartPoints(), x + gp.tileSize*3+50, 270);
        }
    }

    public void drawDialogueBox () {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        // DRAW SUB WINDOW
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // DRAW BORDER
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new java.awt.BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);
    }

    public void drawGoldUI(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        try {
            // GOLD IMAGE
            goldImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/gold.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(goldImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize*9/10, gp.tileSize*9/10, null);
        g2.drawString("Gold: " + gp.player.getGold(), 88, 75);
    }

    public void drawEnergy(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        try {
            energyImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/energy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(energyImage, gp.tileSize/2+7, gp.tileSize+20, goldImage.getWidth()*4/3, goldImage.getHeight()*4/3, null);
        g2.drawString("Energy: " + gp.player.getEnergy(), 88, 120);
    }

    public void drawInventorySelectionScreen() {
        drawDialogueBox();
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("Select item to give", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to confirm, ESC to cancel)", textX, textY + 40);
        
        // Ambil items dari inventory
        java.util.List<model.Item> items = gp.player.getInventory().getItems();
        
        if (items.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
            g2.setColor(Color.RED);
            g2.drawString("No items in inventory!", textX, textY + 100);
            return;
        }

        // Tampilkan item yang dipilih dengan quantity
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        selectedItemDisplay = items.get(inventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 120);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (inventorySelectionIndex + 1) + " of " + items.size(), textX, textY + 160);
    }

    public void drawInventoryScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("INVENTORY", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to select, ESC to close)", textX, textY + 40);
        
        // Ambil items dari inventory
        List<Item> items = gp.player.getInventory().getItems();
        
        if (items.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
            g2.setColor(Color.RED);
            g2.drawString("Inventory is empty!", textX, textY + 100);
            return;
        }

        // Tampilkan item yang dipilih
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        selectedItemDisplay = items.get(inventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 110);
        
        // Info type dan posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Type: " + items.get(inventorySelectionIndex).getType().getDisplayName(), textX, textY + 145);
        g2.drawString("Item " + (inventorySelectionIndex + 1) + " of " + items.size(), textX, textY + 170);
    }

    public void drawEatInventoryScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("SELECT FOOD TO EAT", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to confirm, ESC to cancel)", textX, textY + 40);
        
        // NULL SAFE CHECK untuk filteredItems
        if (filteredItems == null || filteredItems.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
            g2.setColor(Color.RED);
            g2.drawString("No food items available!", textX, textY + 100);
            return;
        }

        // Tampilkan item yang dipilih dari filtered list
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        selectedItemDisplay = filteredItems.get(filteredInventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 110);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (filteredInventorySelectionIndex + 1) + " of " + filteredItems.size(), textX, textY + 150);
        
        // Tampilkan info action
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.CYAN);
        g2.drawString("Action: Restore energy", textX, textY + 185);
    }

    public void drawPlantInventoryScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("SELECT SEEDS TO PLANT", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to confirm, ESC to cancel)", textX, textY + 40);
        
        // NULL SAFE CHECK untuk filteredItems
        if (filteredItems == null || filteredItems.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
            g2.setColor(Color.RED);
            g2.drawString("No seeds available!", textX, textY + 100);
            return;
        }

        // Tampilkan item yang dipilih dari filtered list
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 110);
        selectedItemDisplay = filteredItems.get(filteredInventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 110);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (filteredInventorySelectionIndex + 1) + " of " + filteredItems.size(), textX, textY + 150);
        
        // Tampilkan info action
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.CYAN);
        g2.drawString("Action: Plant in farm", textX, textY + 185);
    }

    public int getXForCenteredText(String text) {
        return gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
    }

    public void drawWorldMapScreen() {
        // Background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Select Map";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        
        // Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        text = "Use W/S to select and ENTER to confirm";
        x = getXForCenteredText(text);
        y = y + gp.tileSize; // Posisi setelah judul
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        
        // Map Options - Kurangi spacing
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        y = y + gp.tileSize; // Mulai dari posisi setelah instruksi
        
        for (int i = 0; i < gp.mapNames.length; i++) {
            text = gp.mapNames[i];
            x = getXForCenteredText(text);
            
            if (i == gp.selectedMap) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, x, y);
            y += gp.tileSize; // Kurangi spacing dari 2 * tileSize menjadi 1 * tileSize
        }
    }
}