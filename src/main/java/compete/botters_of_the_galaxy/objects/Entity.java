package main.java.compete.botters_of_the_galaxy.objects;

import main.java.compete.botters_of_the_galaxy.enums.HeroEnum;
import main.java.compete.botters_of_the_galaxy.enums.UnitTypeEnum;

import java.util.Scanner;

public class Entity {

    int          unitId;
    int          team;
    UnitTypeEnum unitType; // UNIT, HERO, TOWER, can also be GROOT from wood1
    int          x;
    int          y;
    int          attackRange;
    int          health;
    int          maxHealth;
    int          shield; // useful in bronze
    int          attackDamage;
    int          movementSpeed;
    int          stunDuration; // useful in bronze
    int          goldValue;
    int          countDown1; // all countDown and mana variables are useful starting in bronze
    int          countDown2;
    int          countDown3;
    int          mana;
    int          maxMana;
    int          manaRegeneration;
    HeroEnum     heroType; // DEADPOOL, VALKYRIE, DOCTOR_STRANGE, HULK, IRONMAN
    int          isVisible; // 0 if it isn't
    int          itemsOwned; // useful from wood1

    public Entity(Scanner in) {
         unitId = in.nextInt();
         team = in.nextInt();
         String UnitTypeString = in.next();
         if (UnitTypeString != null && !UnitTypeString.isEmpty())
            unitType = UnitTypeEnum.valueOf(UnitTypeString); // UNIT, HERO, TOWER, can also be GROOT from wood1
         x = in.nextInt();
         y = in.nextInt();
         attackRange = in.nextInt();
         health = in.nextInt();
         maxHealth = in.nextInt();
         shield = in.nextInt(); // useful in bronze
         attackDamage = in.nextInt();
         movementSpeed = in.nextInt();
         stunDuration = in.nextInt(); // useful in bronze
         goldValue = in.nextInt();
         countDown1 = in.nextInt(); // all countDown and mana variables are useful starting in bronze
         countDown2 = in.nextInt();
         countDown3 = in.nextInt();
         mana = in.nextInt();
         maxMana = in.nextInt();
         manaRegeneration = in.nextInt();
         String heroTypeString = in.next();
         if (heroTypeString != null && !heroTypeString.isEmpty())
            heroType = HeroEnum.valueOf(heroTypeString); // DEADPOOL, VALKYRIE, DOCTOR_STRANGE, HULK, IRONMAN
         isVisible = in.nextInt(); // 0 if it isn't
         itemsOwned = in.nextInt(); // useful from wood1
    }

    @Override
    public String toString() {
        return "Entity{" + "unitId=" + unitId + ", team=" + team + ", unitType=" + unitType + ", x=" + x + ", y=" + y + ", attackRange=" + attackRange + ", health=" + health + ", maxHealth=" + maxHealth + ", shield=" + shield + ", attackDamage=" + attackDamage + ", movementSpeed=" + movementSpeed + ", stunDuration=" + stunDuration + ", goldValue=" + goldValue + ", countDown1=" + countDown1 + ", countDown2=" + countDown2 + ", countDown3=" + countDown3 + ", mana=" + mana + ", maxMana=" + maxMana + ", manaRegeneration=" + manaRegeneration + ", heroType=" + heroType + ", isVisible=" + isVisible + ", itemsOwned=" + itemsOwned + '}';
    }
}
