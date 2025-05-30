package state;

public class SingleState implements RelationshipState {
    public String getStatusName() { return "Single"; }
    public boolean canPropose() { return true; }
    public boolean canMarry()   { return false; }
}
