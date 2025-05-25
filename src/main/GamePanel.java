package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(1535, 1536)); // pixel per tile = 16; scale = 3; pixel per tile = 48; jumlah tile = 32; total pixel = 1536
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update(); 
            repaint(); 
        }
    }

    public void update() {
        // Update game logic here
    }

    public void drawComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(100, 100, 48, 48); 
        g2.dispose(); 
    }
}

