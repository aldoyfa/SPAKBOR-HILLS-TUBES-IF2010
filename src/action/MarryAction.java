package action;

import entity.NPC;
import entity.Player;
import state.SpouseState;
import java.time.LocalDate;

public class MarryAction implements Action {
    private LocalDate lastProposedDate;

    public boolean execute(Player player, NPC npc) {
        if (!npc.canMarry()) return false;
        // Cek sudah 1 hari berlalu sejak proposal
        if (!player.getCurrentDate().isAfter(lastProposedDate.plusDays(1))) return false;
        npc.setRelationshipState(new SpouseState());
        player.consumeEnergy(80);
        player.skipTimeTo(22, 0);
        return true;
    }
}
