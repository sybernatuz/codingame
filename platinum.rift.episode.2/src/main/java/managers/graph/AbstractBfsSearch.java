package managers.graph;

import objects.Graph;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public abstract class AbstractBfsSearch {

    public Optional<Path> bfsSearch(Graph graph, Zone source) {
        LinkedList<Zone> bfsList = new LinkedList<>();
        Queue<Zone> queue = new LinkedList<>();
        Map<Zone, Zone> prev = new HashMap<>();

        Zone current = source;
        queue.add(current);
        current.isVisited = true;

        while (!queue.isEmpty()) {
            current = queue.remove();
            if (isFound(graph, current))
                break;

            addNextUnvisitedNodes(graph, current, queue, prev);
        }

        if (!isFound(graph, current))
            return Optional.empty();

        for (Zone node = current; node != null; node = prev.get(node))
            bfsList.add(node);

        Collections.reverse(bfsList);
        Path path = new Path();
        path.zones = bfsList;

        return Optional.of(path);
    }

    private void addNextUnvisitedNodes(Graph graph, Zone current, Queue<Zone> queue, Map<Zone, Zone> prev) {
        List<Zone> currentNeighbours = graph.zonesByLinkedZone.get(current);
        currentNeighbours.stream()
                .filter(neighbour -> !neighbour.isVisited)
                .forEach(neighbour-> addNeighbourToQueue(queue, prev, neighbour, current));
    }

    private void addNeighbourToQueue(Queue<Zone> queue, Map<Zone, Zone> prev, Zone neighbour, Zone current) {
        queue.add(neighbour);
        neighbour.isVisited = true;
        prev.put(neighbour, current);
    }

    protected abstract boolean isFound(Graph graph, Zone current);
}
