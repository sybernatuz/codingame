package search;

import objects.Path;
import objects.Zone;
import objects.Graph;

import java.util.*;

public abstract class AbstractBfsSearch implements BfsSearch {


    @Override public Optional<Path> search(Graph graph, Zone source) {
        return search(graph, source, null);
    }

    @Override public Optional<Path> search(Graph graph, Zone source, Zone target) {
        LinkedList<Zone> bfsList = new LinkedList<>();
        Queue<Zone> queue = new LinkedList<>();
        Map<Zone, Zone> prev = new HashMap<>();

        Zone current = source;
        queue.add(current);

        List<Zone> visited = new ArrayList<>();
        visited.add(current);

        while (!queue.isEmpty()) {
            current = queue.remove();
            if (isFound(graph, current, target))
                break;

            addNextUnvisitedNodes(graph, current, queue, prev, visited);
        }

        if (!isFound(graph, current, target))
            return Optional.empty();

        for (Zone node = current; node != null; node = prev.get(node)) {
            bfsList.add(node);
        }

        if (bfsList.isEmpty())
            return Optional.empty();

        Collections.reverse(bfsList);
        Path path = new Path();
        path.zones = bfsList;
        return Optional.of(path);
    }

    private void addNextUnvisitedNodes(Graph graph, Zone current, Queue<Zone> queue, Map<Zone, Zone> prev, List<Zone> visited) {
        List<Zone> currentNeighbours = graph.graph.get(current);
        currentNeighbours.stream()
                .filter(neighbour -> !visited.contains(neighbour))
                .forEach(neighbour-> addNeighbourToQueue(queue, prev, neighbour, current, visited));
    }

    private void addNeighbourToQueue(Queue<Zone> queue, Map<Zone, Zone> prev, Zone neighbour, Zone current, List<Zone> visited) {
        queue.add(neighbour);
        visited.add(neighbour);
        prev.put(neighbour, current);
    }

    protected abstract boolean isFound(Graph graph, Zone current, Zone target);
}
