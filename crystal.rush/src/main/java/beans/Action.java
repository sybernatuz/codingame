package beans;

import java.util.StringJoiner;

public class Action {
    public final String command;
    public final Coord pos;
    public final EntityType item;
    public String message;

    private Action(String command, Coord pos, EntityType item) {
        this.command = command;
        this.pos = pos;
        this.item = item;
    }

    public static Action none() {
        return new Action("WAIT", null, null);
    }

    public static Action move(Coord pos) {
        return new Action("MOVE", pos, null);
    }

    public static Action dig(Coord pos) {
        return new Action("DIG", pos, null);
    }

    public static Action request(EntityType item) {
        return new Action("REQUEST", null, item);
    }

    public String toString() {
        StringJoiner builder = new StringJoiner(" ").add(command);
        if (pos != null) {
            builder.add(pos.toString());
        }
        if (item != null) {
            builder.add(item.name());
        }
        if (message != null) {
            builder.add(message);
        }
        return builder.toString();
    }
}
