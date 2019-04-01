package main.java.compete.legends_of_code_magic.enums;

import java.util.ArrayList;
import java.util.List;

public enum AbilitiesEnum {

    BREAKTHROUGH,
    CHARGE,
    GUARD,
    DRAIN,
    LETHAL,
    WARD;

    public static List<AbilitiesEnum> get(String ability) {
        List<AbilitiesEnum> abilities = new ArrayList<>();
        if (ability.contains("G"))
            abilities.add(AbilitiesEnum.GUARD);
        if (ability.contains("C"))
            abilities.add(AbilitiesEnum.CHARGE);
        if (ability.contains("B"))
            abilities.add(AbilitiesEnum.BREAKTHROUGH);
        if (ability.contains("D"))
            abilities.add(AbilitiesEnum.DRAIN);
        if (ability.contains("L"))
            abilities.add(AbilitiesEnum.LETHAL);
        if (ability.contains("W"))
            abilities.add(AbilitiesEnum.WARD);
        return abilities;
    }
}
