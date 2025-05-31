package inputs;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;
import action.Chatting;
import action.Gift;
import action.Marry;
import action.Propose;
import model.ShopDatabase;
import model.ItemType;


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
            if (code == KeyEvent.VK_P) { // Tekan P untuk plant
                new action.PlantAction(gp);
            }
            if (code == KeyEvent.VK_H) { // Tekan H untuk till (Hoe)
                new action.TillingAction(gp);
            }
            // FARM MANAGEMENT ACTIONS
            if (code == KeyEvent.VK_H) { // Till with Hoe
                new action.TillingAction(gp);
            }
            if (code == KeyEvent.VK_P) { // Plant seeds
                new action.PlantAction(gp);
            }
            if (code == KeyEvent.VK_Q) { // Water plants
                new action.WateringAction(gp);
            }
            if (code == KeyEvent.VK_R) { // Recover tilled land
                new action.RecoverLandAction(gp);
            }
            
            // TAMBAHAN: TIME DISPLAY
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
                // SPECIAL: Check if it's Emily's shop dialogue
                if (gp.ui.isEmilyShop) {
                    // Transition to shop mode selection
                    gp.ui.shopModeIndex = 0;
                    gp.gameState = gp.shopModeState;
                    return; // Don't reset isEmilyShop yet
                }
                
                // Normal dialogue exit (for other NPCs)
                if (gp.ui.currentNPC != null) {
                    gp.ui.currentNPC = null;
                }
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
        

        // PERTAHANKAN HANYA INI - EAT INVENTORY STATE (sudah ada di kode Anda)
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

        // PERTAHANKAN HANYA INI - PLANT INVENTORY STATE (sudah ada di kode Anda)
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
                action.PlantAction.executePlantLogic(gp);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Reset dan kembali ke play state
                gp.ui.filteredItems.clear();
                gp.gameState = gp.playState;
            }
        }
        // SHOP MODE STATE (Buy/Sell)
        else if (gp.gameState == gp.shopModeState) {
            if (code == KeyEvent.VK_LEFT) {
                gp.ui.shopModeIndex = (gp.ui.shopModeIndex + gp.ui.shopModes.length - 1) % gp.ui.shopModes.length;
            }
            if (code == KeyEvent.VK_RIGHT) {
                gp.ui.shopModeIndex = (gp.ui.shopModeIndex + 1) % gp.ui.shopModes.length;
            }
            if (code == KeyEvent.VK_ENTER) {
                String selectedMode = gp.ui.shopModes[gp.ui.shopModeIndex];
                if (selectedMode.equals("Buy")) {
                    // Go to buy category selection
                    gp.ui.shopCategoryIndex = 0;
                    gp.gameState = gp.shopCategoryState;
                } else if (selectedMode.equals("Sell")) {
                    // Go to sell category selection
                    new action.SellAction(gp);
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                // Exit shop completely
                gp.ui.isEmilyShop = false;
                gp.ui.currentNPC = null;
                gp.gameState = gp.playState;
            }
        }
        // SHOP CATEGORY STATE (Buy Categories)
        else if (gp.gameState == gp.shopCategoryState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.shopCategoryIndex > 0) {
                    gp.ui.shopCategoryIndex--;
                } else {
                    gp.ui.shopCategoryIndex = gp.ui.shopCategories.length - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.shopCategoryIndex < gp.ui.shopCategories.length - 1) {
                    gp.ui.shopCategoryIndex++;
                } else {
                    gp.ui.shopCategoryIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Load items for selected category
                ItemType selectedCategory = gp.ui.shopCategories[gp.ui.shopCategoryIndex];
                gp.ui.shopItems = ShopDatabase.getBuyableItemsByCategory(selectedCategory);
                
                if (!gp.ui.shopItems.isEmpty()) {
                    gp.ui.shopItemIndex = 0;
                    gp.gameState = gp.shopItemState;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.shopModeState;
            }
        }
        // SHOP ITEM STATE (Buy Items)
        else if (gp.gameState == gp.shopItemState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.shopItemIndex > 0) {
                    gp.ui.shopItemIndex--;
                } else {
                    gp.ui.shopItemIndex = gp.ui.shopItems.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.shopItemIndex < gp.ui.shopItems.size() - 1) {
                    gp.ui.shopItemIndex++;
                } else {
                    gp.ui.shopItemIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Execute buy logic
                String selectedItem = gp.ui.shopItems.get(gp.ui.shopItemIndex);
                action.BuyAction.executeBuyLogic(gp, selectedItem);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.shopCategoryState;
            }
        }
        // SELL CATEGORY STATE
        else if (gp.gameState == gp.sellCategoryState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.sellCategoryIndex > 0) {
                    gp.ui.sellCategoryIndex--;
                } else {
                    gp.ui.sellCategoryIndex = gp.ui.sellCategories.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.sellCategoryIndex < gp.ui.sellCategories.size() - 1) {
                    gp.ui.sellCategoryIndex++;
                } else {
                    gp.ui.sellCategoryIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Load items for selected sell category
                ItemType selectedCategory = gp.ui.sellCategories.get(gp.ui.sellCategoryIndex);
                gp.ui.sellItems = ShopDatabase.getSellableItemsByCategory(gp, selectedCategory);
                
                if (!gp.ui.sellItems.isEmpty()) {
                    gp.ui.sellItemIndex = 0;
                    gp.gameState = gp.sellItemState;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.shopModeState;
            }
        }
        // SELL ITEM STATE
        else if (gp.gameState == gp.sellItemState) {
            if (code == KeyEvent.VK_UP) {
                if (gp.ui.sellItemIndex > 0) {
                    gp.ui.sellItemIndex--;
                } else {
                    gp.ui.sellItemIndex = gp.ui.sellItems.size() - 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                if (gp.ui.sellItemIndex < gp.ui.sellItems.size() - 1) {
                    gp.ui.sellItemIndex++;
                } else {
                    gp.ui.sellItemIndex = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                // Execute sell logic
                String selectedItem = gp.ui.sellItems.get(gp.ui.sellItemIndex);
                action.SellAction.executeSellLogic(gp, selectedItem);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.sellCategoryState;
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