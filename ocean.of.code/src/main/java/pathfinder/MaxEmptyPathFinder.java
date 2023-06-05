package pathfinder;

import objects.Coordinate;
import objects.actions.Direction;

import java.util.ArrayList;
import java.util.List;

public class MaxEmptyPathFinder {

    private static final MaxEmptyPathFinder INSTANCE = new MaxEmptyPathFinder();

    public static MaxEmptyPathFinder getInstance() {
        return INSTANCE;
    }

    public List<Coordinate> findMaxEmptyPath(Coordinate start, List<Coordinate> alreadyVisited) {
        PathFinderData pathFinderData = new PathFinderData();
        pathFinderData.visited.addAll(alreadyVisited);

        dfs(start, pathFinderData);
        return pathFinderData.maxPath;
    }

    private boolean isValidMove(Coordinate zone, PathFinderData pathFinderData) {
        return zone.isValid() && !pathFinderData.visited.contains(zone);
    }

    private void dfs(Coordinate zone, PathFinderData pathFinderData) {
        if (pathFinderData.maxEmptyZones >= 2000)
            return;

        pathFinderData.visited.add(zone);
        pathFinderData.emptyZone++;
        pathFinderData.currentPath.add(zone);

        if (pathFinderData.emptyZone > pathFinderData.maxEmptyZones) {
            pathFinderData.maxEmptyZones = pathFinderData.emptyZone;
            pathFinderData.maxPath = new ArrayList<>(pathFinderData.currentPath);
        }

        for (Direction direction : Direction.values()) {
            Coordinate nextZone = direction.toCoordinate(zone);
            if (isValidMove(nextZone, pathFinderData))
                dfs(nextZone, pathFinderData);
        }

        pathFinderData.visited.remove(zone);
        pathFinderData.currentPath.remove(pathFinderData.currentPath.size() - 1);
    }
}
