package state;

public class SpouseState implements RelationshipState {
    public String getStatusName() { return "Spouse"; }
    public boolean canPropose() { return false; }
    public boolean canMarry()   { return false; }
}
