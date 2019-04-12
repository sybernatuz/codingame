package main.java.compete.ghost_in_the_cell.objects;

import main.java.compete.ghost_in_the_cell.enums.OwnerEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Factory {

    public int id;
    public OwnerEnum owner;
    public int troopNumber;
    public int production;
    public int arg4;
    public int arg5;
    public List<Link> neighbours;
    public boolean isVisited;

    public Factory(int id) {
        this.id = id;
        neighbours = new ArrayList<>();
        isVisited = false;
    }

    public Factory(Scanner in) {
        owner = OwnerEnum.get(in.nextInt());
        troopNumber = in.nextInt();
        production = in.nextInt();
        arg4 = in.nextInt();
        arg5 = in.nextInt();
    }

    public void update(Factory factory) {
        owner = factory.owner;
        troopNumber = factory.troopNumber;
        production = factory.production;
        arg4 = factory.arg4;
        arg5 = factory.arg5;
        isVisited = false;
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
                '}';
    }
}
