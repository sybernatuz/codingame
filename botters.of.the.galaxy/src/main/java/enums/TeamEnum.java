package enums;

public enum TeamEnum {

    FRIEND,
    ENEMY,
    NEUTRAL;

    public static TeamEnum get(int type, int friendTeam) {
        if (type == -1)
            return TeamEnum.NEUTRAL;

        if (type == friendTeam)
            return TeamEnum.FRIEND;
        return TeamEnum.ENEMY;
    }
}
