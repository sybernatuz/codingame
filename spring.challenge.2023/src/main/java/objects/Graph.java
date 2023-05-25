package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public List<Integer> myBases = new ArrayList<>();
    public List<Integer> enemyBases = new ArrayList<>();
    public Map<Integer, Cell> cells;
    public Map<Cell, List<Cell>> graph = new HashMap<>();

    public Graph(Map<Integer, Cell> cells) {
        this.cells = cells;
        init();
    }

    private void init() {
        cells.values()
                .forEach(cell -> {
                    List<Cell> neighbours = new ArrayList<>();
                    if (cell.neigh0 > 0)
                        neighbours.add(cells.get(cell.neigh0));
                    if (cell.neigh1 > 0)
                        neighbours.add(cells.get(cell.neigh1));
                    if (cell.neigh2 > 0)
                        neighbours.add(cells.get(cell.neigh2));
                    if (cell.neigh3 > 0)
                        neighbours.add(cells.get(cell.neigh3));
                    if (cell.neigh4 > 0)
                        neighbours.add(cells.get(cell.neigh4));
                    if (cell.neigh5 > 0)
                        neighbours.add(cells.get(cell.neigh5));
                    this.graph.put(cell, neighbours);
                });
    }
}
