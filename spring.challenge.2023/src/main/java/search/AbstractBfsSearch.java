package search;

import objects.Cell;
import objects.Graph;

import java.util.*;

public abstract class AbstractBfsSearch implements BfsSearch {

    @Override public Optional<Cell> search(Graph graph, Cell source) {
        Queue<Cell> queue = new LinkedList<>();
        Map<Cell, Cell> prev = new HashMap<>();

        Cell current = source;
        queue.add(current);

        List<Cell> visited = new ArrayList<>();
        visited.add(current);

        while (!queue.isEmpty()) {
            current = queue.remove();
            if (isFound(graph, current))
                return Optional.of(current);

            addNextUnvisitedNodes(graph, current, queue, prev, visited);
        }

        return Optional.empty();
    }

    private void addNextUnvisitedNodes(Graph graph, Cell current, Queue<Cell> queue, Map<Cell, Cell> prev, List<Cell> visited) {
        List<Cell> currentNeighbours = graph.graph.get(current);
        currentNeighbours.stream()
                .filter(neighbour -> !visited.contains(neighbour))
                .forEach(neighbour-> addNeighbourToQueue(queue, prev, neighbour, current, visited));
    }

    private void addNeighbourToQueue(Queue<Cell> queue, Map<Cell, Cell> prev, Cell neighbour, Cell current, List<Cell> visited) {
        queue.add(neighbour);
        visited.add(neighbour);
        prev.put(neighbour, current);
    }

    protected abstract boolean isFound(Graph graph, Cell current);
}
