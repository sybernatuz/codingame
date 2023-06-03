package strategies;

import objects.Coordinate;
import objects.Game;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Type;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TorpedoStrategy {

    private static final TorpedoStrategy INSTANCE = new TorpedoStrategy();

    public static TorpedoStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Game.getInstance().torpedoCooldown > 0) {
            return Optional.empty();
        }

        return findTarget()
                .filter(this::isInRange)
                .map(this::createTorpedo);
    }

    private Optional<Coordinate> findTarget() {
        Submarine enemySubmarine = Game.getInstance().enemySubmarine;
        if (enemySubmarine.coordinate != null) {
            return Optional.of(enemySubmarine.coordinate);
        }
        return Optional.of(enemySubmarine.getDistinctPossibleLocation())
                .filter(locations ->  locations.size() <= 3)
                .filter(this::arePointsIn3x3Area)
                .map(locations -> findIfLocationCanBeTheCenterOf3x3Area(locations)
                        .orElse(calculateMidPointOfArea(locations)));
    }

    private boolean arePointsIn3x3Area(List<PossibleLocation> coordinates) {
        int minX = coordinates.stream()
                .mapToInt(coordinate -> coordinate.x)
                .min()
                .orElse(0);
        int maxX = coordinates.stream()
                .mapToInt(coordinate -> coordinate.x)
                .max()
                .orElse(0);
        int minY = coordinates.stream()
                .mapToInt(coordinate -> coordinate.y)
                .min()
                .orElse(0);
        int maxY = coordinates.stream()
                .mapToInt(coordinate -> coordinate.y)
                .max()
                .orElse(0);

        return (maxX - minX <= 2) && (maxY - minY <= 2);
    }

    private Optional<Coordinate> findIfLocationCanBeTheCenterOf3x3Area(List<PossibleLocation> coordinates) {
        int minX = coordinates.stream()
                .mapToInt(coordinate -> coordinate.x)
                .min()
                .orElse(0);
        int maxX = coordinates.stream()
                .mapToInt(coordinate -> coordinate.x)
                .max()
                .orElse(0);
        int minY = coordinates.stream()
                .mapToInt(coordinate -> coordinate.y)
                .min()
                .orElse(0);
        int maxY = coordinates.stream()
                .mapToInt(coordinate -> coordinate.y)
                .max()
                .orElse(0);

        return coordinates.stream()
                .filter(possibleLocation -> possibleLocation.x >= minX + 1)
                .filter(possibleLocation -> possibleLocation.x <= maxX - 1)
                .filter(possibleLocation -> possibleLocation.y >= minY + 1)
                .filter(possibleLocation -> possibleLocation.y <= maxY - 1)
                .findFirst()
                .map(Function.identity());
    }

    private Coordinate calculateMidPointOfArea(List<PossibleLocation> locations) {
        int x = locations.stream().mapToInt(value -> value.x).sum() / 3;
        int y = locations.stream().mapToInt(value -> value.y).sum() / 3;
        return new Coordinate(x, y);
    }


    private boolean isInRange(Coordinate target) {
        double distanceWithEnemy = Game.getInstance().mySubmarine.coordinateFinal.computeDistance(target);
        boolean targetIsNeighborOfMe = Game.getInstance().mySubmarine.coordinateFinal.isNeighbor(target);
        boolean kill = Game.getInstance().enemySubmarine.coordinate != null && Game.getInstance().enemySubmarine.life <= 2;
        return (!targetIsNeighborOfMe || kill)  && distanceWithEnemy <= 4;
    }

    private Action createTorpedo(Coordinate zone) {
        Action action = new Action();
        action.type = Type.TORPEDO;
        action.coordinate = zone;
        return action;
    }
}
