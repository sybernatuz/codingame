package objects;

import java.util.Objects;

public class Distance {

    public int value;
    public Zone source;
    public Zone target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Objects.equals(target, distance.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }
}
