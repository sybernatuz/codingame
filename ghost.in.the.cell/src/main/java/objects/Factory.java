package objects;


import enums.OwnerEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Factory {

    public int id;
    public OwnerEnum owner;
    public Side side;
    public int troopNumber;
    public int production;
    public Integer initialProduction;
    public int arg4;
    public int arg5;
    public List<Link> neighbours;
    public boolean isVisited;

    public Factory() {
        neighbours = new ArrayList<>();
    }

    public Factory(int id) {
        this.id = id;
        neighbours = new ArrayList<>();
        isVisited = false;
    }


    public void update(Scanner in) {
        owner = OwnerEnum.get(in.nextInt());
        troopNumber = in.nextInt();
        production = in.nextInt();
        arg4 = in.nextInt();
        arg5 = in.nextInt();
        isVisited = false;
        if (initialProduction == null)
            initialProduction = production;
    }

    public Integer getNeighborDistance(Factory neighbor) {
        return neighbours.stream()
                .filter(link -> link.neighbour.equals(neighbor))
                .map(link -> link.distance)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;
        return id == factory.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Factory{" +
                "id=" + id +
                ", owner=" + owner +
                ", troopNumber=" + troopNumber +
                ", production=" + production +
                ", arg4=" + arg4 +
                ", arg5=" + arg5 +
                ", side=" + side +
                '}';
    }
}
