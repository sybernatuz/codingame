package main.java.compete.platinum_rift_episode_2.objects;

import java.util.Objects;

public class Move {

    public int number;
    public Zone zoneSource;
    public Zone zoneTarget;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(zoneSource, move.zoneSource) &&
                Objects.equals(zoneTarget, move.zoneTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneSource, zoneTarget);
    }

    @Override
    public String toString() {
        return "Move{" +
                "number=" + number +
                ", zoneSource=" + zoneSource +
                ", zoneTarget=" + zoneTarget +
                '}';
    }
}
