package objects;

import java.util.*;

public class Historic {

    public Set<Coordinate> coordinates = new HashSet<>();

    public Historic() {
    }

    public Historic(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Historic historic = (Historic) o;
        return Objects.equals(coordinates, historic.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
