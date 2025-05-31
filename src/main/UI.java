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
import model.ItemType;
import model.ShopDatabase;

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

// Variabel untuk filtered inventory (eat/plant actions)
public int filteredInventorySelectionIndex = 0;
public List<Item> filteredItems = new ArrayList<>();

// SHOP VARIABLES
public boolean isEmilyShop = false;
public String[] shopModes = {"Buy", "Sell"};
public int shopModeIndex = 0;
public ItemType[] shopCategories = {ItemType.SEED, ItemType.FOOD, ItemType.MISC};
public int shopCategoryIndex = 0;
public List<String> shopItems = new ArrayList<>();
public int shopItemIndex = 0;

// SELL VARIABLES
public List<ItemType> sellCategories = new ArrayList<>();
public int sellCategoryIndex = 0;
public List<String> sellItems = new ArrayList<>();
public int sellItemIndex = 0;

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

        // INVENTORY STATE (tambahkan ini)
        if (gp.gameState == gp.inventoryState) {
            drawInventoryScreen();
        }

        // HAPUS ATAU COMMENT OUT INI - TIDAK DIPERLUKAN LAGI:
        /*
        // FILTERED INVENTORY STATE (untuk eat/plant actions)
        if (gp.gameState == gp.filteredInventoryState) {
            drawFilteredInventoryScreen();
        }
        */
        
        // PERTAHANKAN HANYA INI - 2 STATE BARU:
        // EAT INVENTORY STATE
        if (gp.gameState == gp.eatInventoryState) {
            drawEatInventoryScreen();
        }
        
        // PLANT INVENTORY STATE  
        if (gp.gameState == gp.plantInventoryState) {
            drawPlantInventoryScreen();
        }
        
        // SHOP MODE SELECTION (Buy/Sell)
        if (gp.gameState == gp.shopModeState) {
            drawShopModeScreen();
        }
        // SHOP CATEGORY SELECTION
        if (gp.gameState == gp.shopCategoryState) {
            drawShopCategoryScreen();
        }
        // SHOP ITEM SELECTION
        if (gp.gameState == gp.shopItemState) {
            drawShopItemScreen();
        }
        // SELL CATEGORY SELECTION
        if (gp.gameState == gp.sellCategoryState) {
            drawSellCategoryScreen();
        }
        // SELL ITEM SELECTION
        if (gp.gameState == gp.sellItemState) {
            drawSellItemScreen();
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
            g2.drawImage(energyImage, x + gp.tileSize*3, 235, energyImage.getWidth()*4/3, energyImage.getHeight()*4/3, null);
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

    // Method untuk menampilkan inventory selection (KHUSUS UNTUK GIFT)
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
        String selectedItemDisplay = items.get(inventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 120);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (inventorySelectionIndex + 1) + " of " + items.size(), textX, textY + 160);
        
        // TAMPILKAN INFO NPC HANYA UNTUK GIFT ACTION
        if (currentNPC != null) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.WHITE);
            g2.drawString("Giving to: " + currentNPC.getName(), textX, textY + 200);
            
            g2.setColor(Color.RED);
            g2.drawString("❤️ Heart Points: " + currentNPC.getHeartPoints(), textX, textY + 230);
        }
    }

    // Method untuk menampilkan inventory lengkap
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
        String selectedItemDisplay = items.get(inventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 120);
        
        // Info type dan posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Type: " + items.get(inventorySelectionIndex).getType().getDisplayName(), textX, textY + 160);
        g2.drawString("Item " + (inventorySelectionIndex + 1) + " of " + items.size(), textX, textY + 190);
    }

    // Method khusus untuk Eat - TANPA currentNPC
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
        String selectedItemDisplay = filteredItems.get(filteredInventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 120);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (filteredInventorySelectionIndex + 1) + " of " + filteredItems.size(), textX, textY + 160);
        
        // Tampilkan info action
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.CYAN);
        g2.drawString("Action: Restore energy", textX, textY + 200);
    }

    // Method khusus untuk Plant - TANPA currentNPC
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
        String selectedItemDisplay = filteredItems.get(filteredInventorySelectionIndex).getSimpleDisplayName();
        g2.drawString("Selected: " + selectedItemDisplay, textX, textY + 120);
        
        // Info posisi item
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Item " + (filteredInventorySelectionIndex + 1) + " of " + filteredItems.size(), textX, textY + 160);
        
        // Tampilkan info action
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.CYAN);
        g2.drawString("Action: Plant in farm", textX, textY + 200);
    }

    // SHOP MODE SELECTION (Buy/Sell)
    public void drawShopModeScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("EMILY'S SHOP - WELCOME!", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(←/→ to move, ENTER to select, ESC to close)", textX, textY + 40);
        
        // Show shop mode options
        int spacing = gp.tileSize * 4;
        for (int i = 0; i < shopModes.length; i++) {
            if (i == shopModeIndex) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                g2.setColor(Color.YELLOW);
            } else {
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
                g2.setColor(Color.WHITE);
            }
            g2.drawString(shopModes[i], textX + (i * spacing), textY + 120);
        }
        
        // Show gold
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.GREEN);
        g2.drawString("Your Gold: " + gp.player.getGold() + "g", textX, textY + 180);
    }

    // SHOP CATEGORY SELECTION
    public void drawShopCategoryScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("EMILY'S SHOP - SELECT CATEGORY", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to select, ESC to back)", textX, textY + 40);
        
        // Show selected category
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.YELLOW);
        String selectedCategory = shopCategories[shopCategoryIndex].getDisplayName();
        g2.drawString("Category: " + selectedCategory, textX, textY + 120);
        
        // Show navigation
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Category " + (shopCategoryIndex + 1) + " of " + shopCategories.length, textX, textY + 160);
        
        // Show gold
        g2.setColor(Color.GREEN);
        g2.drawString("Your Gold: " + gp.player.getGold() + "g", textX, textY + 190);
    }

    // SHOP ITEM SELECTION
    public void drawShopItemScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("EMILY'S SHOP - BUY ITEMS", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to buy, ESC to back)", textX, textY + 40);
        
        if (shopItems.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("No items available in this category!", textX, textY + 100);
            return;
        }

        // Show selected item
        String selectedItem = shopItems.get(shopItemIndex);
        int buyPrice = ShopDatabase.getBuyPrice(selectedItem);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        g2.drawString("Item: " + selectedItem, textX, textY + 120);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.GREEN);
        g2.drawString("Price: " + buyPrice + "g", textX, textY + 160);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Your Gold: " + gp.player.getGold() + "g", textX, textY + 190);
        g2.drawString("Item " + (shopItemIndex + 1) + " of " + shopItems.size(), textX, textY + 215);
        
        // Buy confirmation
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        if (gp.player.getGold() >= buyPrice) {
            g2.setColor(Color.CYAN);
            g2.drawString("Press ENTER to buy", textX, textY + 250);
        } else {
            g2.setColor(Color.RED);
            g2.drawString("Not enough gold!", textX, textY + 250);
        }
    }

    // SELL CATEGORY SELECTION
    public void drawSellCategoryScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("EMILY'S SHOP - SELECT SELL CATEGORY", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to select, ESC to back)", textX, textY + 40);
        
        if (sellCategories.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("No items available to sell!", textX, textY + 100);
            return;
        }
        
        // Show selected category
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.YELLOW);
        String selectedCategory = sellCategories.get(sellCategoryIndex).getDisplayName();
        g2.drawString("Category: " + selectedCategory, textX, textY + 120);
        
        // Show navigation
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("Category " + (sellCategoryIndex + 1) + " of " + sellCategories.size(), textX, textY + 160);
        
        // Show gold
        g2.setColor(Color.GREEN);
        g2.drawString("Your Gold: " + gp.player.getGold() + "g", textX, textY + 190);
    }

    // SELL ITEM SELECTION
    public void drawSellItemScreen() {
        drawDialogueBox();
        
        int boxX = gp.tileSize * 2;
        int boxY = gp.tileSize / 2;
        int textX = boxX + 20;
        int textY = boxY + 60;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString("EMILY'S SHOP - SELL ITEMS", textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("(↑/↓ to move, ENTER to sell, ESC to back)", textX, textY + 40);
        
        if (sellItems.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("No items available in this category!", textX, textY + 100);
            return;
        }

        // Show selected item
        String selectedItem = sellItems.get(sellItemIndex);
        int sellPrice = ShopDatabase.getSellPrice(selectedItem);
        
        // Get quantity dari inventory
        int quantity = gp.player.getInventory().getItemQuantity(selectedItem);
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.YELLOW);
        g2.drawString("Item: " + selectedItem, textX, textY + 120);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.GREEN);
        g2.drawString("Sell Price: " + sellPrice + "g each", textX, textY + 160);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        g2.drawString("You have: " + quantity + " of this item", textX, textY + 190);
        g2.drawString("Your Gold: " + gp.player.getGold() + "g", textX, textY + 215);
        g2.drawString("Item " + (sellItemIndex + 1) + " of " + sellItems.size(), textX, textY + 240);
        
        // Sell confirmation
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.setColor(Color.CYAN);
        g2.drawString("Press ENTER to sell 1 item", textX, textY + 275);
    }

    public int getXForCenteredText(String text) {
        return gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
    }
}