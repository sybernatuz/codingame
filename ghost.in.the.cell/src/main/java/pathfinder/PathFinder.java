package pathfinder;

import game.Game;
import objects.Factory;
import objects.Link;
import objects.Path;
import objects.Side;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinder {

    private static final PathFinder INSTANCE = new PathFinder();

    public static PathFinder getInstance() {
        return INSTANCE;
    }

    public List<Factory> getPathToContestedFactory() {
        Factory middleZone = Game.getInstance().factories.stream()
                .filter(factory -> factory.id == 0)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        List<Factory> path =  middleZone.neighbours.stream()
                .filter(link -> link.neighbour.side.equals(Side.MY_SIDE))
                .sorted(Comparator.comparing((Link link) -> link.distance).reversed())
                .map(link -> link.neighbour)
                .collect(Collectors.toList());

        path.addAll(Game.getInstance().getBySide(Side.CONTESTED));
        return path;
    }

    private int distanceWithTarget(Link link, Factory target) {
        return link.neighbour.neighbours.stream()
                .mapToInt(value -> value.distance)
                .min()
                .orElseThrow(RuntimeException::new);

    }
}
