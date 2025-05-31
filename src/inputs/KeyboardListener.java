package inputs;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;
import action.Chatting;
import action.Gift;
import action.Marry;
import action.Propose;
import action.PlantAction;
import objects.Object;

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
            // TAMBAHKAN TRIGGERS UNTUK ACTIONS
            if (code == KeyEvent.VK_I) { // Tekan I untuk open inventory
                new action.OpenInventoryAction(gp);
            }
            if (code == KeyEvent.VK_E) { // Tekan E untuk eat
                new action.EatAction(gp);
            }
            if (code == KeyEvent.VK_T) { // Show time (T for Time)
                new action.ShowTimeAction(gp);
            }
        }
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                // PERBAIKAN: RESET currentNPC SAAT KELUAR DARI DIALOGUE
                if (gp.ui.currentNPC != null) {
                    gp.ui.currentNPC = null; // Reset NPC setelah dialogue selesai
                }
                gp.gameState = gp.playState;
                if (gp.ui.currentDialogue.contains("energy")) {
                    gp.gameState = gp.playState;
                } else {
                    gp.gameState = gp.playState;
                }
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
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.worldMapState) {
            if (code == KeyEvent.VK_W) {
                gp.selectedMap--;
                if (gp.selectedMap < 0) {
                    gp.selectedMap = gp.mapNames.length - 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.selectedMap++;
                if (gp.selectedMap >= gp.mapNames.length) {
                    gp.selectedMap = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Check energy first before proceeding
                if (gp.player.getEnergy() < 10) {
                    gp.ui.currentDialogue = "Not enough energy to travel! You need at least 10 energy.";
                    gp.gameState = gp.dialogueState;
                    
                    // Reset player position to previous location
                    gp.player.worldX = gp.tileSize * (gp.maxWorldCol/4);
                    gp.player.worldY = gp.tileSize * (gp.maxWorldRow/4);
                    gp.player.direction = "down";
                    
                    // Switch back to play state
                    gp.gameState = gp.playState;
                    return;
                }

                try {
                    // Set map dimensions based on selected map
                    if (gp.selectedMap == 0) { // Farm Map
                        gp.maxWorldCol = gp.maxFarmMapCol;
                        gp.maxWorldRow = gp.maxFarmMapRow;
                    } else { // Other maps (Ocean, Lake, River, Village)
                        gp.maxWorldCol = gp.maxOtherMapCol;
                        gp.maxWorldRow = gp.maxOtherMapRow;
                    }

                    // Clear existing objects
                    gp.obj = new objects.Object[10];

                    // Reinitialize map arrays with new dimensions
                    gp.farmMap.mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

                    // Load selected map
                    gp.farmMap.loadMap(gp.mapPaths[gp.selectedMap]);

                    // Reset player position relative to new map size
                    gp.player.worldX = gp.tileSize * (gp.maxWorldCol/4);
                    gp.player.worldY = gp.tileSize * (gp.maxWorldRow/4);
                    gp.player.direction = "down";

                    // Place objects only if it's farm map
                    if (gp.selectedMap == 0) {
                        gp.objM.setObject();
                    }

                    // Reduce energy by 10
                    gp.player.setEnergy(-10);

                    // Add 15 minutes to game time (3 ticks since 1 tick = 5 minutes)
                    for (int i = 0; i < 3; i++) {
                        gp.farmMap.time.tick();
                    }

                    gp.gameState = gp.playState;
                } catch (Exception ex) {
                    System.out.println("Error loading map: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        else if (gp.gameState == gp.tileActionState) {
            if (code == KeyEvent.VK_LEFT) {
                gp.ui.tileOptionIndex = (gp.ui.tileOptionIndex + gp.ui.tileOptions.length - 1) % gp.ui.tileOptions.length;
            }
            if (code == KeyEvent.VK_RIGHT) {
                gp.ui.tileOptionIndex = (gp.ui.tileOptionIndex + 1) % gp.ui.tileOptions.length;
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                String selected = gp.ui.tileOptions[gp.ui.tileOptionIndex];
                switch (selected) {
                    case "Tiling":
                        new PlantAction (gp, "Tiling");
                        break;
                    case "Recover":
                        new PlantAction (gp, "Recover");
                        break;
                    case "Plant":
                        new PlantAction (gp, "Plant");
                        break;
                    case "Water":
                        new PlantAction (gp, "Water");
                        break;
                    case "Harvest":
                        new PlantAction (gp, "Harvest");
                        break;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        // FIX UNTUK INVENTORY SELECTION STATE (Gift system)
        else if (gp.gameState == gp.inventorySelectionState) {
            // Navigation untuk inventory selection (Gift)
            java.util.List<model.Item> items = gp.player.getInventory().getItems();
            
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.inventorySelectionIndex > 0) {
                    gp.ui.inventorySelectionIndex--;
                } else {
                    // Wrap around ke item terakhir
                    gp.ui.inventorySelectionIndex = items.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.inventorySelectionIndex < items.size() - 1) {
                    gp.ui.inventorySelectionIndex++;
                } else {
                    // Wrap around ke item pertama
                    gp.ui.inventorySelectionIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Konfirmasi pilihan item dan execute gift logic
                action.Gift.executeGiftLogic(gp);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Kembali ke NPC interface
                gp.gameState = gp.NPCInterfaceState;
            }
        }
        else if (gp.gameState == gp.inventoryState) {
            // Navigation untuk inventory lengkap
            java.util.List<model.Item> items = gp.player.getInventory().getItems();
            
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.inventorySelectionIndex > 0) {
                    gp.ui.inventorySelectionIndex--;
                } else {
                    gp.ui.inventorySelectionIndex = items.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.inventorySelectionIndex < items.size() - 1) {
                    gp.ui.inventorySelectionIndex++;
                } else {
                    gp.ui.inventorySelectionIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Untuk sekarang hanya tampilkan info item
                if (!items.isEmpty()) {
                    model.Item selectedItem = items.get(gp.ui.inventorySelectionIndex);
                    gp.ui.currentDialogue = "Item: " + selectedItem.getName() + 
                                           "\nType: " + selectedItem.getType().getDisplayName() + 
                                           "\nQuantity: " + selectedItem.getQuantity();
                    gp.gameState = gp.dialogueState;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        // EAT INVENTORY STATE
        else if (gp.gameState == gp.eatInventoryState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.filteredInventorySelectionIndex > 0) {
                    gp.ui.filteredInventorySelectionIndex--;
                } else {
                    gp.ui.filteredInventorySelectionIndex = gp.ui.filteredItems.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.filteredInventorySelectionIndex < gp.ui.filteredItems.size() - 1) {
                    gp.ui.filteredInventorySelectionIndex++;
                } else {
                    gp.ui.filteredInventorySelectionIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Execute eat logic
                action.EatAction.executeEatLogic(gp);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Reset dan kembali ke play state
                gp.ui.filteredItems.clear();
                gp.gameState = gp.playState;
            }
        }

        // PLANT INVENTORY STATE
        else if (gp.gameState == gp.plantInventoryState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.filteredInventorySelectionIndex > 0) {
                    gp.ui.filteredInventorySelectionIndex--;
                } else {
                    gp.ui.filteredInventorySelectionIndex = gp.ui.filteredItems.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.filteredInventorySelectionIndex < gp.ui.filteredItems.size() - 1) {
                    gp.ui.filteredInventorySelectionIndex++;
                } else {
                    gp.ui.filteredInventorySelectionIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Execute plant logic
                new PlantAction(gp, "Plant").executePlantLogic(gp);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Reset dan kembali ke play state
                gp.ui.filteredItems.clear();
                gp.gameState = gp.playState;
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