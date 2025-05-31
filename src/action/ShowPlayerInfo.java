package action;

import main.GamePanel;

public class ShowPlayerInfo implements Action {
    private GamePanel gp;

    public ShowPlayerInfo(GamePanel gp) {
        this.gp = gp;
        execute();
    }

    @Override
    public void execute() {
        String name = gp.player.getName();
        String gender = gp.player.gender != null ? gp.player.gender : "-";
        int energy = gp.player.getEnergy();
        String partner = gp.player.getPartner() != null ? gp.player.getPartner() : "-";
        String favoriteItem = (gp.player.getFavoriteItem() != null) ? gp.player.getFavoriteItem() : "-";
        int gold = gp.player.getGold();

        System.out.println("Nama: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Energy: " + energy);
        System.out.println("Partner: " + partner);
        System.out.println("Favorite Item: " + favoriteItem);
        System.out.println("Gold: " + gold);

        gp.ui.currentDialogue = "Nama: " + name + "      Favorite Item: " + favoriteItem
                + "\nGender: " + gender + "      Gold: " + gold
                + "\nEnergy: " + energy
                + "\nPartner: " + partner;
        gp.gameState = gp.dialogueState;
    }
}