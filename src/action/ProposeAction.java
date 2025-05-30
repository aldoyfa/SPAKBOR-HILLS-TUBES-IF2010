package action;

import entity.NPC;
import entity.Player;
import state.FianceState;

public class ProposeAction implements Action {
    @Override
    public boolean execute(Player player, NPC npc) {
        if (!npc.canPropose()) return false;
        npc.setRelationshipState(new FianceState());  
        player.consumeEnergy(10);
        player.advanceTimeMinutes(60);
        return true;
    }
}
