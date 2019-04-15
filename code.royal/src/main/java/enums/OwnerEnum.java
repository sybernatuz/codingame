package enums;

public enum OwnerEnum {

    NONE,
    FRIEND,
    ENEMY;

    public static OwnerEnum get(int owner) {
        switch (owner) {
            case -1:
                return NONE;
            case 0:
                return FRIEND;
            case 1:
                return ENEMY;
        }
        return null;
    }
}
