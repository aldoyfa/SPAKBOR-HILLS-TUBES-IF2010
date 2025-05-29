package main;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import inputs.KeyboardListener;
import entity.NPC;
import entity.NPCManager;
import entity.Player;
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

    // FPS
    int FPS = 60; 

    // Game Thread
    Thread gameThread;
    public TileManager tileM = new TileManager(this);
    KeyboardListener keyH = new KeyboardListener();
    public Player player = new Player(this, keyH);

    // ... existing ...
    public NPCManager npcM;       // ← baru
    public boolean inDialogue;    // apakah dialog berjalan?
    public NPC currentNPC;        // NPC yang di-interact
    public int dialogueIndex;     // baris dialog sekarang
    private boolean prevSpacePressed = false;
    private boolean prevEnterPressed = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        npcM = new NPCManager(this);  // setelah keyH & player siap
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
        // deteksi rising edge
        boolean spaceTap = keyH.spacePressed && !prevSpacePressed;
        boolean enterTap = keyH.enterPressed && !prevEnterPressed;

        // simpan state sekarang untuk frame berikutnya
        prevSpacePressed = keyH.spacePressed;
        prevEnterPressed = keyH.enterPressed;

        if (!inDialogue) {
            player.update();

            // mulai dialog hanya sekali saat Space baru ditekan
            if (spaceTap) {
                Rectangle pr = new Rectangle(
                player.worldX + player.solidArea.x,
                player.worldY + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height
            );
                NPC hit = npcM.checkCollisionNPC(pr);
                if (hit != null) {
                    currentNPC    = hit;
                    inDialogue    = true;
                    dialogueIndex = 0;
                }
            }
        } else {
            // hanya maju dialog sekali tiap Enter atau Space baru ditekan
            if (enterTap || spaceTap) {
                dialogueIndex++;
                // jika melewati batas, keluar dialog
                if (dialogueIndex >= currentNPC.getDialogueCount()) {
                    inDialogue    = false;
                    dialogueIndex = 0;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        // Graphics2D g2 = (Graphics2D) g;
        // tileM.draw(g2);
        // player.draw(g2);
        // g2.dispose();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        npcM.draw(g2);
        player.draw(g2);

        if (inDialogue) {
            drawDialogueBox(g2, currentNPC.getDialogue(dialogueIndex));
        }
        g2.dispose();
    }   

    private void drawDialogueBox(Graphics2D g2, String text) {
        int x = 50, y = screenHeight - 150;
        int w = screenWidth - 100, h = 120;
        g2.setColor(new Color(0,0,0,200));
        g2.fillRoundRect(x, y, w, h, 20, 20);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, w, h, 20, 20);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString(text, x + 20, y + 50);
    }
}

