package objects;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    public int gold;
    public Coordinate mapCenter;
    public Coordinate start;
    public Coordinate opposedY;
    public List<Coordinate> extremities;
    public boolean isFirstAction;

    public GameInfo() {
        computeMapCenter();
        computeExtremities();
        isFirstAction = true;
    }

    private void computeMapCenter() {
        Coordinate center = new Coordinate();
        center.x = 1920 / 2;
        center.y = 1000 / 2;
        this.mapCenter = center;
    }

    private void computeExtremities() {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(0, 0));
        coordinates.add(new Coordinate(0, 1000));
        coordinates.add(new Coordinate(1920, 0));
        coordinates.add(new Coordinate(1920, 1000));
        this.extremities = coordinates;
    }
}
