package state;

public class FianceState implements RelationshipState {
    public String getStatusName() { return "Fiance"; }
    public boolean canPropose() { return false; }
    public boolean canMarry()   { return true; }
}
