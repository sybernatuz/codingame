package objects;

import java.util.StringJoiner;

public class Action {

    public Type type;
    public Factory source;
    public Factory target;
    public Integer number;

    public Action(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ")
                .add(type.name())
                .add(String.valueOf(source.id));

        if (target != null)
            stringJoiner.add(String.valueOf(target.id));
        if (number != null)
            stringJoiner.add(String.valueOf(number));

        return stringJoiner.toString();
    }

    public enum Type {
        MOVE,
        BOMB,
        INC;
    }


    public static final class MoveBuilder {
        private Factory source;
        private Factory target;
        private Integer number;

        private MoveBuilder() {
        }

        public static MoveBuilder of(Factory source) {
            MoveBuilder moveBuilder = new MoveBuilder();
            moveBuilder.source = source;
            return moveBuilder;
        }

        public MoveBuilder withTarget(Factory target) {
            this.target = target;
            return this;
        }

        public MoveBuilder withNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Action build() {
            Action action = new Action(Type.MOVE);
            action.source = this.source;
            action.number = this.number;
            action.target = this.target;

            this.source.troopNumber -= this.number;
            return action;
        }
    }
}
