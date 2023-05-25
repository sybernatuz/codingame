package enums;

import java.util.Arrays;

public enum ItemRarityEnum {

    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLDEN("Golden"),
    LEGENDARY("Legendary");

    public final String label;

    ItemRarityEnum(String label) {
        this.label = label;
    }

    public static ItemRarityEnum fromString(String value) {
        return Arrays.stream(ItemRarityEnum.values())
                .filter(itemRarityEnum -> value.contains(itemRarityEnum.label))
                .findFirst()
                .orElse(null);
    }
}
