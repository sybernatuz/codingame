package objects;

import java.util.Objects;
import java.util.StringJoiner;

public class Action {

    public Integer index1;
    public Integer index2;
    public Integer strength;
    public Type type;

    public Action(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add(type.name());
        if (index1 != null) {
            stringJoiner.add(index1.toString());
        }
        if (index2 != null) {
            stringJoiner.add(index2.toString());
        }
        if (strength != null) {
            stringJoiner.add(strength.toString());
        }
        return stringJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return index1.equals(action.index1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index1);
    }

    public static enum Type {
        LINE,
        BEACON,
        WAIT;
    }
}
