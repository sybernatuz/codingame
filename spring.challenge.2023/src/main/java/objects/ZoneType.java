package objects;

import java.util.Arrays;

public enum ZoneType {
    EMPTY(0),
    EGG(1),
    FOOD(2),
    MY_BASE(3),
    ENEMY_BASE(4);

    private final int value;

    ZoneType(int value) {
        this.value = value;
    }

    public static ZoneType fromValue(int value) {
        return Arrays.stream(ZoneType.values())
                .filter(zoneType -> zoneType.value == value)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
