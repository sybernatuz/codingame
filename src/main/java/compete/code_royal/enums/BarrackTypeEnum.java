package main.java.compete.code_royal.enums;

public enum BarrackTypeEnum {

    NONE(0),
    KNIGHT(80),
    ARCHER(100),
    GIANT(140);

    public int cost;

    BarrackTypeEnum(int cost) {
        this.cost = cost;
    }

    public static BarrackTypeEnum get(int type) {
        switch (type) {
            case -1:
                return BarrackTypeEnum.NONE;
            case 0:
                return BarrackTypeEnum.KNIGHT;
            case 1:
                return BarrackTypeEnum.ARCHER;
            case 2:
                return BarrackTypeEnum.GIANT;
        }
        return BarrackTypeEnum.NONE;
    }
}
