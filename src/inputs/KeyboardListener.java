package inputs;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import main.GamePanel;
import action.Chatting;
import action.Gift;
import action.Marry;
import action.Propose;
import action.FishingAction;
import action.PlantAction;
import model.Fish;
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
        else if (gp.gameState == gp.fishingGuessState) {
            char c = e.getKeyChar();
            if (Character.isDigit(c)) {
                gp.ui.fishingInput += c;
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
            if (code == KeyEvent.VK_L) {
                new action.ShowLocationAction(gp);
            }
            if (code == KeyEvent.VK_F) {
                FishingAction.execute(gp);
            }
            if (code == KeyEvent.VK_V) {
                new action.ShowPlayerInfo(gp);
            }
            if (code == KeyEvent.VK_C) {
                model.Recipe recipe = model.RecipeDatabase.getRecipeByName("Baguette"); // contoh nyata
                if (recipe != null) {
                    action.CookingAction.execute(gp, recipe);
                } else {
                    gp.ui.currentDialogue = "Recipe not found.";
                    gp.gameState = gp.dialogueState;
                }
            }
        }
        else if (gp.gameState == gp.creditsState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.titleState;
            }
        }
        else if (gp.gameState == gp.helpState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }
        }
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.isEmilyShop) {
                    gp.ui.shopModeIndex = 0;
                    gp.gameState = gp.shopModeState;
                    return;
                }

                // Check Shipping Bin transition
                if (gp.ui.isShippingBinMode) {
                    // Directly go to sell categories (no buy/sell choice)
                    new action.SellAction(gp);
                    return;
                }
                if (gp.ui.currentNPC != null) {
                    gp.ui.currentNPC = null; // Reset NPC setelah dialogue selesai
                }
                gp.ui.isEmilyShop = false;
                gp.ui.isShippingBinMode = false;
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
                    if (gp.ui.playerName.isEmpty()) {
                        gp.ui.playerName = "Player"; // Default name
                    }
                    else {
                        gp.player.setName(gp.ui.playerName);
                    }
                    gp.player.gender = (gp.ui.chooseIndex == 0) ? "male" : "female";
                    if (gp.ui.farmName.isEmpty()) {
                        gp.ui.farmName = "My Farm"; // Default name
                    }
                    else {
                        gp.farmMap.setName(gp.ui.farmName);
                    }
                    gp.gameState = gp.playState;
                    gp.ui.selectingGender = false;
                    gp.ui.enteringPlayerName = true;
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
                if (gp.player.getEnergy() <= 10) {
                    gp.ui.currentDialogue = "You are too tired to travel!";
                    gp.gameState = gp.dialogueState;
                    return;
                }
                else {
                    gp.player.setEnergy(-10); // Kurangi energi saat memilih map
                    try {
                    // Set map dimensions based on selected map
                    if (gp.selectedMap == 0) { // Farm Map
                        gp.maxWorldCol = gp.maxFarmMapCol;
                        gp.maxWorldRow = gp.maxFarmMapRow;
                    } else if (gp.selectedMap == 5) { // NpcHouse/Flooring map
                        gp.maxWorldCol = 24; // Set exact size for flooring
                        gp.maxWorldRow = 24;
                    } else { // Other maps (Ocean, Lake, River, Village)
                        gp.maxWorldCol = gp.maxOtherMapCol;
                        gp.maxWorldRow = gp.maxOtherMapRow;
                    }
                    // Reset map array with new dimensions
                    gp.farmMap.mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

                    // Load selected map
                    gp.farmMap.loadMap(gp.mapPaths[gp.selectedMap]);

                    // Set spawn points based on map type
                    if (gp.selectedMap == 5) { // NpcHouse/Flooring
                        gp.player.worldX = gp.tileSize * 12; // Center of 24x24 map
                        gp.player.worldY = gp.tileSize * 12;
                    } else if (gp.selectedMap == 4) { // Village
                        gp.player.worldX = gp.tileSize * 4;
                        gp.player.worldY = gp.tileSize * 4;
                    } else {
                        gp.player.worldX = gp.tileSize * (gp.maxWorldCol/4);
                        gp.player.worldY = gp.tileSize * (gp.maxWorldRow/4);
                    }

                    gp.player.direction = "down";
                    gp.gameState = gp.playState;

                } catch (Exception ex) {
                    System.out.println("Error loading map: " + ex.getMessage());
                    ex.printStackTrace();
                }
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
        else if (gp.gameState == gp.fishingGuessState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState; // langsung kembali ke mode main
                return;
            }

            if (code == KeyEvent.VK_BACK_SPACE && gp.ui.fishingInput.length() > 0) {
                gp.ui.fishingInput = gp.ui.fishingInput.substring(0, gp.ui.fishingInput.length() - 1);
            } 
            else if (code == KeyEvent.VK_ENTER) {
                try {
                    int guess = Integer.parseInt(gp.ui.fishingInput);
                    if (guess == gp.ui.fishingTarget) {
                        gp.ui.fishingSuccess = true;
                        Fish caught = gp.ui.selectedFish;
                        gp.player.addItem(caught.getName(), 1, model.ItemType.FISH);
                        gp.ui.currentDialogue = "You caught a " + caught.getName() + "!";
                        gp.gameState = gp.dialogueState;
                    } else {
                        gp.ui.fishingAttempt++;
                        gp.ui.fishingInput = "";

                        if (gp.ui.fishingAttempt > gp.ui.fishingMaxAttempts) {
                            gp.ui.currentDialogue = "Failed to catch a fish...";
                            gp.gameState = gp.dialogueState;
                        }
                    }
                } catch (NumberFormatException ignored) {
                    gp.ui.fishingInput = "";
                }
            }
        } 
                // SHOP MODE STATE (Buy/Sell)
        else if (gp.gameState == gp.shopModeState) {
            if (code == KeyEvent.VK_ENTER) {
                // Emily only has buy mode now
                gp.ui.shopCategoryIndex = 0;
                gp.gameState = gp.shopCategoryState;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.isEmilyShop = false;
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
                // ENHANCED: Clean exit shipping bin dengan reset semua flags
                gp.ui.isShippingBinMode = false;
                gp.ui.currentNPC = null; // Reset currentNPC juga
                gp.gameState = gp.playState;
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
                // Execute shipping logic
                String selectedItem = gp.ui.sellItems.get(gp.ui.sellItemIndex);
                action.SellAction.executeShipLogic(gp, selectedItem);
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