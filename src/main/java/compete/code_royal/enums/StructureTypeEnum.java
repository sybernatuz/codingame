package main.java.compete.code_royal.enums;

public enum StructureTypeEnum {

    NONE,
    MINE,
    BARRACK,
    TOWER;

    public static StructureTypeEnum get(int type) {
        switch (type) {
            case -1:
                return StructureTypeEnum.NONE;
            case 0:
                return StructureTypeEnum.MINE;
            case 1:
                return StructureTypeEnum.TOWER;
            case 2:
                return StructureTypeEnum.BARRACK;
        }
        return StructureTypeEnum.NONE;
    }
}