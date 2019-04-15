package enums;

public enum OwnerEnum {

    FRIEND,
    ENEMY,
    NONE;

    public static OwnerEnum get(int owner) {
        switch (owner) {
            case 1: return OwnerEnum.FRIEND;
            case -1: return OwnerEnum.ENEMY;
        }
        return OwnerEnum.NONE;
    }
}
