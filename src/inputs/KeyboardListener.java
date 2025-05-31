package inputs;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;
import action.Chatting;
import action.Gift;
import action.Marry;
import action.Propose;

public class KeyboardListener implements KeyListener {
    GamePanel gp;
    public boolean wPressed, aPressed, sPressed, dPressed, enterPressed;

    public KeyboardListener(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped (KeyEvent e) {
        if (gp.gameState == gp.newGameState) {
            char c = e.getKeyChar();
            if (Character.isLetterOrDigit(c) || c == ' ') {
                if (gp.ui.enteringPlayerName) {
                    gp.ui.playerName += c;
                } else if (gp.ui.enteringFarmName) {
                    gp.ui.farmName += c;
                }
            }
        }
    }

    public void keyPressed (KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                enterPressed = true;
            }
        }
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.newGameState) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.enteringPlayerName && gp.ui.playerName.length() > 0) {
                    gp.ui.playerName = gp.ui.playerName.substring(0, gp.ui.playerName.length() - 1);
                } else if (gp.ui.enteringFarmName && gp.ui.farmName.length() > 0) {
                    gp.ui.farmName = gp.ui.farmName.substring(0, gp.ui.farmName.length() - 1);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (gp.ui.enteringPlayerName) {
                    gp.ui.enteringPlayerName = false;
                    gp.ui.enteringFarmName = true;
                } else if (gp.ui.enteringFarmName) {
                    gp.ui.enteringFarmName = false;
                    gp.ui.selectingGender = true;
                } else if (gp.ui.selectingGender) {
                    // Set ke player & lanjut ke main game
                    gp.player.setName(gp.ui.playerName);
                    gp.player.gender = (gp.ui.chooseIndex == 0) ? "male" : "female";
                    gp.farmMap.setName(gp.ui.farmName);
                    gp.gameState = gp.playState;
                }
            } else if (gp.ui.selectingGender) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gp.ui.chooseIndex = 1 - gp.ui.chooseIndex;
                }
            }
        }
        else if (gp.gameState == gp.NPCInterfaceState) {
            if (code == KeyEvent.VK_LEFT) {
                gp.ui.npcOptionIndex = (gp.ui.npcOptionIndex + gp.ui.npcOptions.length - 1) % gp.ui.npcOptions.length;
            }
            if (code == KeyEvent.VK_RIGHT) {
                gp.ui.npcOptionIndex = (gp.ui.npcOptionIndex + 1) % gp.ui.npcOptions.length;
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                String selected = gp.ui.npcOptions[gp.ui.npcOptionIndex];
                switch (selected) {
                    case "Chat":
                        new Chatting (gp, gp.ui.currentNPC);
                        break;
                    case "Gift":
                        new Gift (gp, gp.ui.currentNPC);
                        break;
                    case "Propose":
                        new Propose (gp, gp.ui.currentNPC);
                        break;
                    case "Marry":
                        new Marry (gp, gp.ui.currentNPC);
                        break;
                }
            }
        }
        else if (gp.gameState == gp.worldMapState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.selectedMap--;
                if (gp.selectedMap < 0) {
                    gp.selectedMap = gp.mapNames.length - 1;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.selectedMap++;
                if (gp.selectedMap >= gp.mapNames.length) {
                    gp.selectedMap = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                try {
                    // Load map yang dipilih
                    gp.farmMap.loadMap(gp.mapPaths[gp.selectedMap]);
                    // Reset posisi player ke tengah map
                    gp.player.worldX = gp.tileSize * (gp.maxWorldCol/2);
                    gp.player.worldY = gp.tileSize * (gp.maxWorldRow/2);
                    gp.player.direction = "down";
                    // Reset object placement sesuai map baru
                    gp.farmMap.placeObjects();
                    gp.gameState = gp.playState;
                } catch (Exception ex) {
                    System.out.println("Error loading map: " + ex.getMessage());
                }
            }
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
    }
}