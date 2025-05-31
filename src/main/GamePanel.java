package main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import model.FarmMap;
import objects.Object;
import render.ObjectRenderer;
import entity.Player;


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

    // FPS
    int FPS = 60; 

    // SYSTEM
    public UI ui = new UI(this);
    public KeyboardListener keyH = new KeyboardListener(this);
    public MyMouseListener mouseH = new MyMouseListener(this);
    Thread gameThread;
    long timeCounter = 0; // Counter untuk waktu game

    // GAME STATE - TAMBAHKAN STATE YANG HILANG
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int newGameState = 3; // PERBAIKAN: UBAH KE 3
    public final int dialogueState = 4; 
    public final int characterState = 5; // PERBAIKAN: GESER KE 5
    public final int tradeState = 6; // GESER
    public final int fishingState = 7; // GESER
    public final int travelState = 8; // GESER
    public final int sellingState = 9; // GESER
    public final int viewPlayerInfoState = 10; // GESER
    public final int viewHelpState = 11; // GESER
    public final int sleepingState = 12; // GESER
    public final int chattingState = 13; // GESER
    public final int giftingState = 14; // GESER
    public final int proposingState = 15; // GESER
    public final int marryingState = 16; // GESER
    public final int npcInteractionState = 17; // GESER
    public final int cookingState = 18; // GESER
    public final int cookingExecutionState = 19; // GESER

    // TAMBAHKAN STATE BARU:
    public final int NPCInterfaceState = 20;
    public final int inventorySelectionState = 21; // KHUSUS GIFT
    public final int inventoryState = 22; // INVENTORY LENGKAP
    public final int eatInventoryState = 23; // KHUSUS EAT
    public final int plantInventoryState = 24; // KHUSUS PLANT

    public int newGameCounter = 0;

    // ENTITY AND OBJECT
    public FarmMap farmMap;
    public Player player;
    public Object[] obj;
    public ObjectRenderer objM;
    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        // ENTITY AND OBJECT
        farmMap = new FarmMap(this);
        player = new Player(this, keyH);
        obj = new Object[10]; 
        objM = new ObjectRenderer(this);
    }

    public void setupGame() {
        farmMap.renderer.getTileImage();
        objM.setObject(); 
        gameState = titleState;
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
                farmMap.time.tick();
                timeCounter = 0;

                int hour = farmMap.time.getHour();
                int minute = farmMap.time.getMinute();

                // // Auto sleep jam 02:00–05:59
                // if (hour >= 2 && hour < 6 && !player.hasSleptToday) {
                //     player.autoSleep();
                // }

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
        if (gameState == playState) {
            player.update(); 
        }
        if (gameState == pauseState) {
            
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // PLAY STATE
        else {
            farmMap.renderer.draw(g2);
            objM.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        }
        g2.dispose(); 
    }
}