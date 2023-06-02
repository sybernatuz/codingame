package pathfinder;

import objects.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class PathFinderData {
    public List<Coordinate> visited = new ArrayList<>();
    public int maxEmptyZones = 0;
    public int emptyZone = 0;
    public List<Coordinate> maxPath = new ArrayList<>();
    public List<Coordinate> currentPath = new ArrayList<>();
}
