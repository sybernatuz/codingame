package main.java.compete.botters_of_the_galaxy.objects;

import main.java.compete.botters_of_the_galaxy.enums.HeroEnum;
import main.java.compete.botters_of_the_galaxy.enums.TeamEnum;
import main.java.compete.botters_of_the_galaxy.enums.UnitTypeEnum;

import java.util.Objects;
import java.util.Scanner;

public class Entity {
    public int unitId;
    public TeamEnum team;
    public UnitTypeEnum unitType; // UNIT, HERO, TOWER, can also be GROOT from wood1
    public Coordinate coordinate;
    public int attackRange;
    public int health;
    public int maxHealth;
    public int shield; // useful in bronze
    public int attackDamage;
    public int movementSpeed;
    public int stunDuration; // useful in bronze
    public int goldValue;
    public int countDown1; // all countDown and mana variables are useful starting in bronze
    public int countDown2;
    public int countDown3;
    public int mana;
    public int maxMana;
    public int manaRegeneration;
    public HeroEnum heroType; // DEADPOOL, VALKYRIE, DOCTOR_STRANGE, HULK, IRONMAN
    public int isVisible; // 0 if it isn't
    public int itemsOwned; // useful from wood1

    public Entity(Scanner in, int myTeam) {
        unitId = in.nextInt();
        team = TeamEnum.get(in.nextInt(), myTeam);
        String UnitTypeString = in.next();
        if (UnitTypeString != null && !UnitTypeString.isEmpty())
            unitType = UnitTypeEnum.valueOf(UnitTypeString);
        int x = in.nextInt();
        int y = in.nextInt();
        coordinate = new Coordinate(x, y);
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
        if (heroTypeString != null && !heroTypeString.isEmpty() && !heroTypeString.equals("-"))
            heroType = HeroEnum.valueOf(heroTypeString);
        isVisible = in.nextInt(); // 0 if it isn't
        itemsOwned = in.nextInt(); // useful from wood1
    }

    @Override
    public String toString() {
        return "Entity{" +
                "unitId=" + unitId +
                ", team=" + team +
                ", unitType=" + unitType +
                ", coordinate=" + coordinate +
                ", attackRange=" + attackRange +
                ", health=" + health +
                ", maxHealth=" + maxHealth +
                ", shield=" + shield +
                ", attackDamage=" + attackDamage +
                ", movementSpeed=" + movementSpeed +
                ", stunDuration=" + stunDuration +
                ", goldValue=" + goldValue +
                ", countDown1=" + countDown1 +
                ", countDown2=" + countDown2 +
                ", countDown3=" + countDown3 +
                ", mana=" + mana +
                ", maxMana=" + maxMana +
                ", manaRegeneration=" + manaRegeneration +
                ", heroType=" + heroType +
                ", isVisible=" + isVisible +
                ", itemsOwned=" + itemsOwned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return unitId == entity.unitId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId);
    }
}
