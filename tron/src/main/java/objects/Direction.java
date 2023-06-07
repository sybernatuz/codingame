package objects;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction fromPosition(Coordinate coordinate, Coordinate coordinateToGo) {
        int dx = coordinate.x - coordinateToGo.x;
        int dy = coordinate.y - coordinateToGo.y;
        if (dx == 1 && dy == 0)
            return LEFT;
        if (dx == -1 && dy == 0)
            return RIGHT;
        if (dx == 0 && dy == 1)
            return UP;
        if (dx == 0 && dy == -1)
            return DOWN;
        return null;
    }

    public Coordinate move(Coordinate source) {
        switch (this) {
            case UP: return new Coordinate(source.x, source.y - 1);
            case DOWN: return new Coordinate(source.x, source.y + 1);
            case LEFT: return new Coordinate(source.x - 1, source.y);
            case RIGHT: return new Coordinate(source.x + 1, source.y);
        }
        throw new RuntimeException();
    }
}
