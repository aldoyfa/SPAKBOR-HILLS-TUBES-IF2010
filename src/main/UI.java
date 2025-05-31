package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.FontFormatException;
import java.io.InputStream;
import javax.imageio.ImageIO;

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

    // Variabel untuk inventory selection
    public int inventorySelectionIndex = 0;
    public NPC targetNPC; // NPC yang akan menerima gift

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

        // NPC NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.drawString(currentNPC.getName(), x, 270);

        // Heart Points
        try {
            // GOLD IMAGE
            energyImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/energy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(energyImage, x + gp.tileSize*3, 235, goldImage.getWidth()*4/3, goldImage.getHeight()*4/3, null);
        g2.drawString("Heart Points: " + currentNPC.getHeartPoints(), x + gp.tileSize*3+50, 270);
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
        int x = gp.tileSize * 3;
        int y = gp.tileSize * 2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        g2.setColor(Color.WHITE);
        g2.drawString("Select item to give (↑/↓ to move, ENTER to confirm, ESC to cancel):", x, y);
        
        // Ambil items dari inventory
        java.util.List<model.Item> items = gp.player.getInventory().getItems();
        
        if (items.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
            g2.setColor(Color.RED);
            g2.drawString("No items in inventory!", x, y + gp.tileSize);
            return;
        }

        // Tampilkan setiap item dalam list
        for (int i = 0; i < items.size(); i++) {
            if (i == inventorySelectionIndex) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(items.get(i).getName(), x, y + gp.tileSize + (i * 60));
        }
    }

    public int getXForCenteredText(String text) {
        return gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
    }
}