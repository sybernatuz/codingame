package pathfinder;

import objects.Coordinate;

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
        if (pathFinderData.maxPath.size() >= 30)
            return;

        pathFinderData.visited.add(zone);
        pathFinderData.emptyZone++;
        pathFinderData.currentPath.add(zone);

        if (pathFinderData.emptyZone > pathFinderData.maxEmptyZones) {
            pathFinderData.maxEmptyZones = pathFinderData.emptyZone;
            pathFinderData.maxPath = new ArrayList<>(pathFinderData.currentPath);
        }

        // Possible movements: right, down, left, up
        int[][] movements = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};


        for (int[] movement : movements) {
            int nextX = zone.x + movement[0];
            int nextY = zone.y + movement[1];

            Coordinate nextZone = new Coordinate(nextX, nextY);
            if (isValidMove(nextZone, pathFinderData))
                dfs(nextZone, pathFinderData);
        }

        pathFinderData.visited.remove(zone);
        pathFinderData.currentPath.remove(pathFinderData.currentPath.size() - 1);
    }
}
