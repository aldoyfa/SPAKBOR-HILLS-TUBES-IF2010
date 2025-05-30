package action;

import entity.NPC;
import entity.Player;

public class ChattingAction implements Action {
    @Override
    public boolean execute(Player player, NPC npc) {
        npc.changeHeartPoints(+10);               // +10 :contentReference[oaicite:5]{index=5}
        player.consumeEnergy(10);
        player.advanceTimeMinutes(10);
        return true;
    }
}
