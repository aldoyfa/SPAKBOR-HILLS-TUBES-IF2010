package inputs;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;
import action.Chatting;
import action.Gift;
import action.Marry;
import action.Propose;
import objects.NPC;

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
        else if (gp.gameState == gp.inventorySelectionState) {
            // Navigation untuk inventory selection
            java.util.List<model.Item> items = gp.player.getInventory().getItems();
            
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.inventorySelectionIndex > 0) {
                    gp.ui.inventorySelectionIndex--;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.inventorySelectionIndex < items.size() - 1) {
                    gp.ui.inventorySelectionIndex++;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Konfirmasi pilihan item dan execute gift logic
                if (!items.isEmpty() && gp.ui.inventorySelectionIndex < items.size()) {
                    NPC targetNPC = gp.ui.targetNPC;
                    
                    // Cek apakah player memiliki energy untuk memberikan gift
                    if (gp.player.getEnergy() >= 5) {
                        // Execute gift logic
                        String reaction = "";
                        int heartPointsGain = 0;
                        
                        // Evaluasi gift berdasarkan preferensi NPC (asumsi method evaluateGift ada)
                        // Untuk sementara menggunakan logic sederhana
                        heartPointsGain = 20; // Default value
                        reaction = targetNPC.getName() + " accepts the gift. +" + heartPointsGain + " heart points!";
                        
                        // Update heart points NPC
                        targetNPC.setHeartPoints(heartPointsGain);
                        
                        // Hapus item dari inventory
                        gp.player.getInventory().getItems().remove(gp.ui.inventorySelectionIndex);
                        
                        // Update energy player
                        gp.player.setEnergy(-5);
                        
                        // Set dialogue dan pindah ke dialogue state
                        gp.ui.currentDialogue = reaction;
                        gp.gameState = gp.dialogueState;
                    } else {
                        // Not enough energy
                        gp.ui.currentDialogue = "You don't have enough energy to give gifts!";
                        gp.gameState = gp.dialogueState;
                    }
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Kembali ke NPC interface
                gp.gameState = gp.NPCInterfaceState;
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
        // if (code == KeyEvent.VK_SPACE) {
        //     spacePressed = false;
        // }
        // if (code == KeyEvent.VK_ENTER){
        //     enterPressed = false; 
        // } 
        // if (code == KeyEvent.VK_UP)    upPressed    = true;
        // if (code == KeyEvent.VK_DOWN)  downPressed  = true;
    }
}