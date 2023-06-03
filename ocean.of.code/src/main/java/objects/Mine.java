package objects;

public class Mine extends Coordinate {

    public boolean active = false;

    public Mine(Coordinate coordinate) {
        super(coordinate.x, coordinate.y);
    }
}
