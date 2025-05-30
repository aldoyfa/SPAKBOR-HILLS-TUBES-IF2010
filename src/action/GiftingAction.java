package action;

import entity.NPC;
import entity.Player;

public class GiftingAction implements Action {
    private String itemName;

    public GiftingAction(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean execute(Player player, NPC npc) {
        if (!player.getInventory().remove(itemName)) return false;
        int delta = npc.evaluateGift(itemName);  // +25/+20/–25 :contentReference[oaicite:4]{index=4}
        npc.changeHeartPoints(delta);
        player.consumeEnergy(5);
        player.advanceTimeMinutes(10);
        return true;
    }
}
