package main.java.compete.ghost_in_the_cell.utils;

import main.java.compete.ghost_in_the_cell.enums.OwnerEnum;
import main.java.compete.ghost_in_the_cell.objects.Factory;
import main.java.compete.ghost_in_the_cell.objects.Path;

import java.util.*;

public class GraphUtils {

    public static Path findPathToFriendFactoryInDanger(Factory source) {
        LinkedList<Factory> bfsList = new LinkedList<>();
        Queue<Factory> queue = new LinkedList<>();
        Map<Factory, Factory> prev = new HashMap<>();

        Factory current = source;
        queue.add(current);
        current.isVisited = true;
        Factory enemyBaseLinked = null;

        while (!queue.isEmpty()) {
            current = queue.remove();
            enemyBaseLinked = findLinkedEnemyBase(current);
            if (enemyBaseLinked != null)
                break;
            addNextUnvisitedNodes(current, queue, prev);
        }

        if (enemyBaseLinked == null)
            return null;

        for (Factory node = enemyBaseLinked; node != null; node = prev.get(node))
            bfsList.add(node);

        Collections.reverse(bfsList);
        return new Path(bfsList);
    }

    private static void addNextUnvisitedNodes(Factory current, Queue<Factory> queue, Map<Factory, Factory> prev) {
        current.neighbours.stream()
                .map(link -> link.neighbour)
                .filter(currentNeighbour -> !currentNeighbour.isVisited)
                .forEach(currentNeighbour -> addNeighbourToQueue(currentNeighbour, current, queue, prev));
    }

    private static void addNeighbourToQueue(Factory currentNeighbour, Factory current, Queue<Factory> queue, Map<Factory, Factory> prev) {
        queue.add(currentNeighbour);
        currentNeighbour.isVisited = true;
        prev.put(currentNeighbour, current);
    }

    private static Factory findLinkedEnemyBase(Factory current) {
        return current.neighbours.stream()
                .map(link -> link.neighbour)
                .filter(factory -> factory.owner.equals(OwnerEnum.ENEMY))
                .findFirst()
                .orElse(null);
    }
}
