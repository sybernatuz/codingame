package objects;

public class Link {

    public Factory neighbour;
    public int distance;

    @Override
    public String toString() {
        return "Link{" +
                "neighbour=" + neighbour.id +
                ", distance=" + distance +
                '}';
    }
}
