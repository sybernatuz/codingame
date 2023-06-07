package objects;

public enum Direction {

    FORWARD(1),
    BACKWARD(-1);

    public final int value;

    Direction(int value) {
        this.value = value;
    }
}
