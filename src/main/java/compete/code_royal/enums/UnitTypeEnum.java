package main.java.compete.code_royal.enums;

public enum UnitTypeEnum {

    QUEEN,
    KNIGHT,
    ARCHER,
    GIANT;

    public static UnitTypeEnum get(int type) {
        switch (type) {
            case -1:
                return UnitTypeEnum.QUEEN;
            case 0:
                return UnitTypeEnum.KNIGHT;
            case 1:
                return UnitTypeEnum.ARCHER;
            case 2:
                return UnitTypeEnum.GIANT;
        }
        return null;
    }
}
