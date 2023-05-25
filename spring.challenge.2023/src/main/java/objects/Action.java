package objects;

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

    public static enum Type {
        LINE,
        BEACON,
        WAIT;
    }
}
