package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Cursor;
import main.GamePanel;

public class MyMouseListener implements MouseListener, MouseMotionListener {

	private GamePanel gp;

	public MyMouseListener(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (gp.gameState == gp.titleState) {
			if (gp.ui.newGameRect.contains(x, y)) {
				gp.ui.hoveredButton = 0;
			} else if (gp.ui.loadGameRect.contains(x, y)) {
				gp.ui.hoveredButton = 1;
			} else if (gp.ui.creditsRect.contains(x, y)) {
				gp.ui.hoveredButton = 2;
			} else if (gp.ui.exitRect.contains(x, y)) {
				gp.ui.hoveredButton = 3;
			} else {
				gp.ui.hoveredButton = -1;
			}
		}
		if (gp.gameState == gp.pauseState) {
			if (gp.ui.continueRect.contains(x, y)) {
				gp.ui.hoveredButton = 4;
			} else if (gp.ui.helpButtonRect.contains(x, y)) {
				gp.ui.hoveredButton = 5;
			} else if (gp.ui.saveRect.contains(x, y)) {
				gp.ui.hoveredButton = 6;
			} else if (gp.ui.exitRect.contains(x, y)) {
				gp.ui.hoveredButton = 7;
			} else {
				gp.ui.hoveredButton = -1;
			}
		}
		if (gp.ui.hoveredButton != -1) {
    		gp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			gp.setCursor(Cursor.getDefaultCursor());
		}
		gp.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    int x = e.getX();
    	int y = e.getY();

		if (gp.gameState == gp.playState) {
			int col = (e.getX() + gp.player.worldX - gp.player.screenX) / gp.tileSize;
			int row = (e.getY() + gp.player.worldY - gp.player.screenY) / gp.tileSize;
			if (col >= 10 && col < 42 && row >= 9 && row < 41) {
				gp.ui.plantingCol = col;
				gp.ui.plantingRow = row;
				gp.gameState = gp.tileActionState;
			}
		}

		if (gp.gameState == gp.titleState) {
			if (gp.ui.newGameRect.contains(x, y)) {
				gp.resetWorld();
				gp.newGameCounter = 1;
				gp.gameState = gp.newGameState;
			} else if (gp.ui.loadGameRect.contains(x, y)) {
				System.out.println("Load game clicked");
				// Implementasi load game
			} else if (gp.ui.creditsRect.contains(x, y)) {
				gp.gameState = gp.creditsState;
				// Tampilkan credit screen atau ubah state
			} else if (gp.ui.exitRect.contains(x, y)) {
				System.exit(0);
			}
		}
		if (gp.gameState == gp.pauseState) {
			if (gp.ui.helpButtonRect.contains(x, y)) {
				gp.gameState = gp.helpState;
			} else if (gp.ui.continueRect.contains(x, y)) {
				gp.gameState = gp.playState;
			} else if (gp.ui.saveRect.contains(x, y)) {
				System.out.println("Save game clicked");
				// Implementasi save game
			} else if (gp.ui.exitRect.contains(x, y)) {
				gp.gameState = gp.titleState;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}