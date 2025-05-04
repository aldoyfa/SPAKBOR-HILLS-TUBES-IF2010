package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

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

    }
}

