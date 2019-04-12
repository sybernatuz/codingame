package main.java.compete.ghost_in_the_cell.objects;

public class Link {

    public Factory neighbour;
    public int distance;

    @Override
    public String toString() {
        return "Link{" +
                "neighbour=" + neighbour +
                ", distance=" + distance +
                '}';
    }
}
