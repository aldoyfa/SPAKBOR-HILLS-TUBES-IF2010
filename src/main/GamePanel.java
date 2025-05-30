package main;

import javax.swing.JPanel;

import Objects.ObjectManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import inputs.KeyboardListener;
import entity.Player;
import Objects.Object;
import tile.TileManager;

import action.*;

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

    // FPS
    int FPS = 60; 

    // Objects
    Thread gameThread;
    public TileManager tileM = new TileManager(this);
    public Object obj[] = new Object[10]; 
    public ObjectManager objM = new ObjectManager(this);
    KeyboardListener keyH = new KeyboardListener();
    public Player player = new Player(this, keyH);
    
    // Untuk menghitung detik (agar bisa update waktu setiap 1 detik)
    long timeCounter = 0;
    public boolean showInventory = false;
    public boolean showTime = false;
    public boolean showLocation = false;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
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

            // Tambahan: timeCounter naik setiap frame
            timeCounter++;

            // Jika sudah 60 frame (1 detik), tambahkan 5 menit waktu game
            if (timeCounter >= FPS) {
                player.time.tick();
                timeCounter = 0;
            
                int hour = player.time.getHour();
                int minute = player.time.getMinute();
            
                // Auto sleep jam 02:00–05:59
                if (hour >= 2 && hour < 6 && !player.hasSleptToday) {
                    player.autoSleep();
                }
            
                // Reset flag tidur saat pagi
                if (hour == 6 && minute == 0) {
                    player.hasSleptToday = false;
                }
            }            

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; 

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval; 
            }
            catch (InterruptedException e) {
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
        
        // HUD Overlay
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        g2.setColor(Color.WHITE);

        int hudX = 20;
        int hudY = 60;
        int hudWidth = 350;
        int lineHeight = 25;
        int padding = 15;

        if (showInventory) {
            int boxHeight = (player.inventory.getItems().size() + 2) * lineHeight + padding;
            g2.setColor(new Color(0, 0, 0, 150)); // hitam transparan
            g2.fillRoundRect(hudX - 10, hudY - 30, hudWidth, boxHeight, 20, 20);

            g2.setColor(Color.WHITE);
            g2.drawString("Inventory:", hudX, hudY);
            int y = hudY + lineHeight;
            for (model.Item item : player.inventory.getItems()) {
                g2.drawString("- " + item.getName(), hudX + 20, y);
                y += lineHeight;
            }
        }

        if (showTime) {
            int timeY = 250;
            int timeHeight = 4 * lineHeight + padding;
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(hudX - 10, timeY - 30, hudWidth, timeHeight, 20, 20);
        
            g2.setColor(Color.WHITE);
            int textY = timeY;
            g2.drawString("Hari: ke-" + player.time.getDay(), hudX, textY);
            textY += lineHeight;
            g2.drawString("Musim: " + player.time.getSeason(), hudX, textY);
            textY += lineHeight;
            g2.drawString(String.format("Jam: %02d:%02d", player.time.getHour(), player.time.getMinute()), hudX, textY);
            textY += lineHeight;
            g2.drawString("Cuaca: " + player.time.getWeather(), hudX, textY);
        }        

        if (showLocation) {
            int locationY = 410;
            int locationHeight = 2 * lineHeight + padding;
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(hudX - 10, locationY - 30, hudWidth, locationHeight, 20, 20);
        
            int tileX = player.worldX / tileSize;
            int tileY = player.worldY / tileSize;
        
            g2.setColor(Color.WHITE);
            g2.drawString("Lokasi Tile: (" + tileX + ", " + tileY + ")", hudX, locationY);
            g2.drawString("Koordinat: (" + player.worldX + ", " + player.worldY + ")", hudX, locationY + lineHeight);
        }
        
        g2.dispose(); 
    }
}