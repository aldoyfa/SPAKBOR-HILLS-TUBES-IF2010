package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    public boolean wPressed, aPressed, sPressed, dPressed;
    public boolean iPressed, tPressed, lPressed;
    public boolean onePressed, twoPressed, threePressed, fourPressed, fivePressed;
    public boolean upPressed, downPressed, enterPressed, escPressed;

    public void keyTyped (KeyEvent e) {
    }

    public void keyPressed (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (code == KeyEvent.VK_I) {
            iPressed = true;
        }
        if (code == KeyEvent.VK_T) {
            tPressed = true;
        }
        if (code == KeyEvent.VK_L) {
            lPressed = true;
        }
        if (code == KeyEvent.VK_1) {
            onePressed = true;
        }
        if (code == KeyEvent.VK_2) {
            twoPressed = true;
        }
        if (code == KeyEvent.VK_3) {
            threePressed = true;
        }
        if (code == KeyEvent.VK_4) {
            fourPressed = true;
        }
        if (code == KeyEvent.VK_5) {
            fivePressed = true;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }
    }

    public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (code == KeyEvent.VK_1) {
            onePressed = false;
        }
        if (code == KeyEvent.VK_2) {
            twoPressed = false;
        }
        if (code == KeyEvent.VK_3) {
            threePressed = false;
        }
        if (code == KeyEvent.VK_4) {
            fourPressed = false;
        }
        if (code == KeyEvent.VK_5) {
            fivePressed = false;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
    }
}