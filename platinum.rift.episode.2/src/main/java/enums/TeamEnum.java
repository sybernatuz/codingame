package enums;

public enum TeamEnum {

    FRIEND,
    ENEMY,
    NONE;

    public static TeamEnum get(int type, int friendTeam) {
        if (type == -1)
            return NONE;
        if (type == friendTeam)
            return TeamEnum.FRIEND;
        return TeamEnum.ENEMY;
    }
}
