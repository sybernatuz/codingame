package objects.actions;

import objects.Coordinate;

import java.util.StringJoiner;

public class Action {
    public Type type;
    public Coordinate coordinate;
    public Integer sector;
    public Direction direction;
    public Type powerCharge;


    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add(type.name());
        if (direction != null) {
            stringJoiner.add(direction.name());
        }
        if (coordinate != null) {
            stringJoiner.add(String.valueOf(coordinate.x));
            stringJoiner.add(String.valueOf(coordinate.y));
        }
        if (sector != null) {
            stringJoiner.add(sector.toString());
        }
        if (powerCharge != null) {
            stringJoiner.add(powerCharge.name());
        }
        return stringJoiner.toString();
    }

}
