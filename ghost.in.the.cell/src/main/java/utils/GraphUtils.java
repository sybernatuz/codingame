package utils;


import enums.OwnerEnum;
import objects.Factory;
import objects.Path;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphUtils {

    public static Path findPathToFriendFactoryInDanger(Factory source, List<Factory> factories) {
        LinkedList<Factory> bfsList = new LinkedList<>();
        Queue<Factory> queue = new LinkedList<>();
        Map<Factory, Factory> prev = new HashMap<>();

        Factory current = source;
        queue.add(current);
        current.isVisited = true;
        Factory enemyBaseLinked = null;

        while (!queue.isEmpty()) {
            current = queue.remove();
            current = findFactory(current, factories);
            enemyBaseLinked = findLinkedEnemyBase(current);
            if (enemyBaseLinked != null) {
                bfsList.add(current);
                break;
            }
            addNextUnvisitedNodes(current, queue, prev);
        }

        if (enemyBaseLinked == null)
            return null;

        for (Factory node = enemyBaseLinked; node != null; node = prev.get(node))
            bfsList.add(node);

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

    private static Factory findFactory(Factory current, List<Factory> factories) {
        return factories.stream()
                .filter(factory -> factory.equals(current))
                .findFirst()
                .orElse(null);
    }
}
