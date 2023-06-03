package objects;

import java.util.Objects;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", sector=" + computeSector() +
                '}';
    }

    public int computeDistance(Coordinate coordinate) {
        return Math.abs(x - coordinate.x) + Math.abs(y - coordinate.y);
    }

    public boolean isNeighbor(Coordinate coordinate) {
        int dx = Math.abs(x - coordinate.x);
        int dy = Math.abs(y - coordinate.y);

        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1) || (dx == 0 && dy == 0);
    }

    public int computeSector() {
        return (y / 5) * 3 + (x / 5) + 1;
    }

    public boolean isSectorExtremity() {
        return  (x % 5 == 0 || x % 5 == 4 || y % 5 == 0 || y % 5 == 4);
    }

    public boolean isValid() {
        return x >= 0
                && y >= 0
                && x < Grid.getInstance().width
                && y < Grid.getInstance().height
                && !Grid.getInstance().blocked.contains(this);
    }

}
