package objects;

public enum Team {
    ALLY,
    ENEMY;

    static Team fromPlayerNumber(int playerNumber, int allyNumber) {
        if (playerNumber == allyNumber)
            return ALLY;
        return ENEMY;
    }
}
