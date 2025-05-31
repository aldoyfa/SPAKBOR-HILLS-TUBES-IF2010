package main;

import Objects.Object;
import Objects.ObjectManager;
import entity.Player;
import inputs.KeyboardListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.FarmTile;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tiles
    final int scale = 4; // Scale factor

    public final int tileSize = originalTileSize * scale; // 64x64 tiles
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    // World Settings 
    public final int maxWorldCol = 52;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public FarmTile[][] farmMap;

    // FPS
    int FPS = 60;

    // Objects
    Thread gameThread;
    public TileManager tileM = new TileManager(this);
    public Object obj[] = new Object[10];
    public ObjectManager objM = new ObjectManager(this);
    KeyboardListener keyH = new KeyboardListener();
    public Player player = new Player(this, keyH);

    // HUD states
    long timeCounter = 0;
    public boolean showInventory = false;
    public boolean showTime = false;
    public boolean showLocation = false;
    public boolean showActionMenu = false;

    // Detailed action menu states
    public boolean showDetailedActionMenu = false;
    public int detailedActionMenuIndex = 0;

    // Message display system
    private List<String> messages = new ArrayList<>();
    private List<Integer> messageTimers = new ArrayList<>();
    private final int MESSAGE_DISPLAY_TIME = 180; // frames (~3 seconds at 60 FPS)

    // Action menu items
    public static class ActionMenuItem {
        public String name;
        public String description;
        public int energyCost;
        public int timeCost; // in minutes
        public String requiredItem;

        public ActionMenuItem(String name, String description, int energyCost, int timeCost, String requiredItem) {
            this.name = name;
            this.description = description;
            this.energyCost = energyCost;
            this.timeCost = timeCost;
            this.requiredItem = requiredItem;
        }
    }

    public ActionMenuItem[] detailedActionMenuItems = new ActionMenuItem[] {
        new ActionMenuItem("Tilling", "Mengubah land menjadi soil", 5, 5, "Hoe"),
        new ActionMenuItem("Recover Land", "Mengubah soil menjadi land", 5, 5, "Pickaxe"),
        new ActionMenuItem("Planting", "Menanam seed di soil.", 5, 5, "Seed yang hendak ditanam"),
        new ActionMenuItem("Watering", "Bergerak ke soil yang telah ditanam dan melakukan watering.", 5, 5, "Watering Can")
    };

    public static final int PLAY_STATE = 0;
    public static final int END_GAME_STATE = 1;
    public int gameState = PLAY_STATE;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        farmMap = new FarmTile[maxWorldCol][maxWorldRow];
        for (int x = 0; x < maxWorldCol; x++) {
            for (int y = 0; y < maxWorldRow; y++) {
                farmMap[x][y] = new FarmTile();
            }
        }
    }
    public void setupGame() {
        objM.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            timeCounter++;
            if (timeCounter >= FPS) {
                player.time.tick(); // Tambah 5 menit
                timeCounter = 0;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();

        if (keyH.iPressed) {
            showInventory = !showInventory;
            keyH.iPressed = false;
        }

        if (keyH.tPressed) {
            showTime = !showTime;
            keyH.tPressed = false;
        }

        if (keyH.lPressed) {
            showLocation = !showLocation;
            keyH.lPressed = false;
        }
        
        // Update message timers and remove expired messages
        for (int i = 0; i < messageTimers.size(); i++) {
            int time = messageTimers.get(i) - 1;
            messageTimers.set(i, time);
            if (time <= 0) {
                messageTimers.remove(i);
                messages.remove(i);
                i--;
            }
        }

        // Detailed action menu navigation and selection
        if (showDetailedActionMenu) {
            if (keyH.upPressed) {
                detailedActionMenuIndex--;
                if (detailedActionMenuIndex < 0) {
                    detailedActionMenuIndex = detailedActionMenuItems.length - 1;
                }
                keyH.upPressed = false;
            }
            if (keyH.downPressed) {
                detailedActionMenuIndex++;
                if (detailedActionMenuIndex >= detailedActionMenuItems.length) {
                    detailedActionMenuIndex = 0;
                }
                keyH.downPressed = false;
            }
            if (keyH.enterPressed) {
                executeDetailedAction(detailedActionMenuIndex);
                showDetailedActionMenu = false;
                keyH.enterPressed = false;
            }
            if (keyH.escPressed) {
                showDetailedActionMenu = false;
                keyH.escPressed = false;
            }
        } else {
            if (keyH.onePressed) {
                showDetailedActionMenu = true;
                detailedActionMenuIndex = 0;
                keyH.onePressed = false;
            }
        }
    }

    public void addMessage(String message) {
        messages.add(message);
        messageTimers.add(MESSAGE_DISPLAY_TIME);
    }

    private void executeDetailedAction(int index) {
        int tileX = player.worldX / tileSize;
        int tileY = player.worldY / tileSize;
        ActionMenuItem item = detailedActionMenuItems[index];

        switch (item.name) {
            case "Tilling":
                player.till(this, tileX, tileY);
                break;
            case "Recover Land":
                player.recover(this, tileX, tileY);
                break;
            case "Planting":
                break;
            case "Watering":
                player.water(this, tileX, tileY);
                break;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        if (showInventory) {
            g2.drawString("Inventory:", 20, 60);
            int y = 90;
            for (model.Item item : player.inventory.getItems()) {
                g2.drawString("- " + item.getName(), 40, y);
                y += 25;
            }
        }

        if (showTime) {
            g2.drawString(player.time.getCurrentTime(), 20, 300);
        }

        if (showLocation) {
            int tileX = player.worldX / tileSize;
            int tileY = player.worldY / tileSize;
            g2.drawString("Lokasi Tile: (" + tileX + ", " + tileY + ")", 20, 340);
            g2.drawString("Koordinat: (" + player.worldX + ", " + player.worldY + ")", 20, 370);
        }
        
        if (showActionMenu) {
            g2.drawString("Action Menu:", 20, 420);
            g2.drawString("1. Eat", 40, 450);
            g2.drawString("2. Plant", 40, 475);
            g2.drawString("3. Fish", 40, 500);
            g2.drawString("4. Harvest", 40, 525);
            g2.drawString("5. Water", 40, 550);
        }

        if (showDetailedActionMenu) {
            int x = 50;
            int y = 100;
            int width = 500;
            int height = 200;
            int arc = 20;

            // Draw background box
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRoundRect(x, y, width, height, arc, arc);

            // Draw border
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, width, height, arc, arc);

            // Draw title and instructions
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString("Select Action (↑/↓ to move, ENTER to confirm, ESC to cancel)", x + 20, y + 30);

            // Draw menu items
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            int itemY = y + 60;
            for (int i = 0; i < detailedActionMenuItems.length; i++) {
                ActionMenuItem item = detailedActionMenuItems[i];
                if (i == detailedActionMenuIndex) {
                    g2.setColor(Color.YELLOW);
                    g2.drawString("> " + item.name, x + 20, itemY);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.drawString(item.name, x + 20, itemY);
                }
                itemY += 30;
            }
        }

        // Draw messages on screen
        int msgX = 20;
        int msgY = 100;
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        for (int i = 0; i < messages.size(); i++) {
            g2.setColor(Color.WHITE);
            g2.drawString(messages.get(i), msgX, msgY);
            msgY += 25;
        }

        g2.dispose();
    }
    
}
