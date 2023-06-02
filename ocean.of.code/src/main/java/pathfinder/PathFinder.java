package pathfinder;

import objects.Coordinate;
import objects.Grid;

import java.util.List;

public class PathFinder {

    private static final PathFinder INSTANCE = new PathFinder();

    public static PathFinder getInstance() {
        return INSTANCE;
    }

    public List<Coordinate> findPath() {
        List<Coordinate> maxSizedSquare = MaximumSizedSquareFinder.getInstance().findMaximumSquare(Grid.getInstance().asArray);
        return SnailMovementPathFinder.getInstance().snailPath(maxSizedSquare);
    }
}
