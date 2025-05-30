package action;

import entity.NPC;
import entity.Player;

public interface Action {
    /**  
     * @return true jika aksi berhasil dijalankan  
     */
    boolean execute(Player player, NPC npc);
}
