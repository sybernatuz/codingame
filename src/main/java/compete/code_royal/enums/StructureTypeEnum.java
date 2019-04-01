package main.java.compete.code_royal.enums;

public enum StructureTypeEnum {

    NONE,
    BARRACK,
    TOWER;

    public static StructureTypeEnum get(int type) {
        switch (type) {
            case -1:
                return NONE;
            case 1:
                return TOWER;
            case 2:
                return BARRACK;
        }
        return NONE;
    }
}