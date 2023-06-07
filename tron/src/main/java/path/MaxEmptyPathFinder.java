package path;

import objects.Coordinate;
import objects.Direction;
import objects.Game;
import objects.ZoneType;

import java.util.ArrayList;
import java.util.List;

public class MaxEmptyPathFinder {

    private static final MaxEmptyPathFinder INSTANCE = new MaxEmptyPathFinder();

    public static MaxEmptyPathFinder getInstance() {
        return INSTANCE;
    }

    public List<Coordinate> findMaxEmptyPath(Coordinate start) {
        PathFinderData pathFinderData = new PathFinderData();
        dfs(start, pathFinderData);
        pathFinderData.maxPath.remove(start);
        return pathFinderData.maxPath;
    }

    private void dfs(Coordinate zone, PathFinderData pathFinderData) {
        if (pathFinderData.maxEmptyZones >= 10000)
            return;

        pathFinderData.visited.add(zone);
        pathFinderData.emptyZone++;
        pathFinderData.currentPath.add(zone);

        if (pathFinderData.emptyZone > pathFinderData.maxEmptyZones) {
            pathFinderData.maxEmptyZones = pathFinderData.emptyZone;
            pathFinderData.maxPath = new ArrayList<>(pathFinderData.currentPath);
        }

        for (Direction direction : Direction.values()) {
            Coordinate nextZone = direction.move(zone);
            if (isValidMove(nextZone, pathFinderData))
                dfs(nextZone, pathFinderData);
        }

        pathFinderData.visited.remove(zone);
        pathFinderData.currentPath.remove(pathFinderData.currentPath.size() - 1);
    }

    private boolean isValidMove(Coordinate zone, PathFinderData pathFinderData) {
        return zone.isValid()
                && !pathFinderData.visited.contains(zone)
                && ZoneType.FREE.equals(Game.getInstance().grid[zone.y][zone.x]);
    }
}
