package beans;

public enum EntityType {
    NOTHING,
    ALLY_ROBOT,
    ENEMY_ROBOT,
    RADAR,
    TRAP,
    AMADEUSIUM;

    public static EntityType valueOf(int id) {
        return values()[id + 1];
    }
}
