package observer;

import entity.NPC;

public interface RelationshipObserver {
    void onRelationshipChanged(NPC npc);
}
