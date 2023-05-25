package objects;

import java.util.Arrays;

public enum CellType {
    EMPTY(0),
    EGG(1),
    FOOD(2);

    private final int value;

    CellType(int value) {
        this.value = value;
    }

    public static CellType fromValue(int value) {
        return Arrays.stream(CellType.values())
                .filter(cellType -> cellType.value == value)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
