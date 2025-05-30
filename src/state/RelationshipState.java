package state;

public interface RelationshipState {
    String getStatusName();
    /** bolehkan melakukan aksi tertentu */
    boolean canPropose();
    boolean canMarry();
}