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

}
