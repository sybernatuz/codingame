package objects;


import enums.OwnerEnum;
import enums.UnitTypeEnum;

import java.util.Scanner;

public class Unit {

    public Coordinate coordinate;
    public OwnerEnum owner;
    public UnitTypeEnum unitType; // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER
    public int health;

    public Unit(Scanner in) {
        Coordinate coordinate = new Coordinate();
        coordinate.x = in.nextInt();
        coordinate.y = in.nextInt();
        this.coordinate = coordinate;
        owner = OwnerEnum.get(in.nextInt());
        unitType = UnitTypeEnum.get(in.nextInt()); // -1 = QUEEN, 0 = KNIGHT, 1 = ARCHER
        health = in.nextInt();
    }

    @Override
    public String toString() {
        return "Unit{" + "coordinate=" + coordinate + ", owner=" + owner + ", unitType=" + unitType + ", health=" + health + '}';
    }
}
