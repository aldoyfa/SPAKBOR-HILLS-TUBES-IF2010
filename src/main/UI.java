package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Color;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage goldImage;
    Font handjet_30;

    public UI(GamePanel gp) {
        this.gp = gp;
        handjet_30 = new Font("Handjet", Font.PLAIN, 30);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
    
        if (gp.gameState == gp.playState) {
            drawGoldUI();
        
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight * 2 / 8;
        g2.drawString(text, x, y);
    }

    public void drawGoldUI(){
        g2.setFont(handjet_30);
        g2.setColor(Color.WHITE);
        try {
            // GOLD IMAGE
            goldImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/gold.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(goldImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize*9/10, gp.tileSize*9/10, null);
        g2.drawString("Gold: " + gp.player.getGold(), 88, 75);
    }

    public int getXForCenteredText(String text) {
        return gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
    }
}
