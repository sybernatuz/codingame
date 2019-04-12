package main.java.compete.ghost_in_the_cell.objects;

import java.util.ArrayList;
import java.util.List;

public class Path {

    public List<Factory> factories;

    public Path() {
    }

    public Path(List<Factory> path) {
        this.factories = path;
    }

    @Override
    public String toString() {
        return "Path{" +
                "factories=" + factories +
                '}';
    }
}
