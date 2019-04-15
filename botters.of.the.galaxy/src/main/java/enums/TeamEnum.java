package enums;

public enum TeamEnum {

    FRIEND,
    ENEMY;

    public static TeamEnum get(int type, int friendTeam) {
        if (type == friendTeam)
            return TeamEnum.FRIEND;
        return TeamEnum.ENEMY;
    }
}
