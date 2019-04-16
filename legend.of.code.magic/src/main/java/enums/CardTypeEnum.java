package enums;

public enum CardTypeEnum {
    CREATURE,
    GREEN_ITEM,
    RED_ITEM,
    BLUE_ITEM;

    public static CardTypeEnum get(int type) {
        switch (type) {
            case 0: return CardTypeEnum.CREATURE;
            case 1: return CardTypeEnum.GREEN_ITEM;
            case 2: return CardTypeEnum.RED_ITEM;
            case 3: return CardTypeEnum.BLUE_ITEM;
        }
        return CardTypeEnum.CREATURE;
    }
}
