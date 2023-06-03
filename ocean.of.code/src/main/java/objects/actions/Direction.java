package objects.actions;

import objects.Coordinate;

public enum Direction {
    N,
    S,
    E,
    W;

    public static Direction getDirection(Coordinate source, Coordinate toGo) {
        if (source.x > toGo.x)
            return W;
        if (source.x < toGo.x)
            return E;
        if (source.y < toGo.y)
            return S;
        if (source.y > toGo.y)
            return N;
        throw new RuntimeException();
    }

    public Coordinate toCoordinate(Coordinate source) {
        int x = source.x;
        int y = source.y;
        switch (this) {
            case E:
                x++;
                break;
            case W:
                x--;
                break;
            case N:
                y--;
                break;
            case S:
                y++;
                break;
        }
        return new Coordinate(x, y);
    }

}
