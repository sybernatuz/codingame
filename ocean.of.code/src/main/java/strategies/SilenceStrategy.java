package strategies;

import objects.Coordinate;
import objects.Game;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SilenceStrategy {

    private static final SilenceStrategy INSTANCE = new SilenceStrategy();

    static SilenceStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Game.getInstance().silenceCooldown > 0) {
            return Optional.empty();
        }
        if (!needSilence()) {
            return Optional.empty();
        }
        Coordinate zoneToGo = Game.getInstance().bestPath.get(Game.getInstance().step);
        Action action = new Action();
        action.type = Type.SILENCE;
        action.direction = Direction.getDirection(Game.getInstance().mySubmarine.coordinateFinal, zoneToGo);
        action.sector = computeDistance(action.direction);
        if (action.sector < 2)
            return Optional.empty();

        Game.getInstance().step += action.sector;
        Game.getInstance().mySubmarine.spotted = false;
        Game.getInstance().mySubmarine.coordinateToSilence = null;
        return Optional.of(action);
    }

    private boolean needSilence() {
        Submarine mySubmarine = Game.getInstance().mySubmarine;
        long possibilitiesAddedIfSilence = computePossibilitiesAddedIfSilence();
        return possibilitiesAddedIfSilence >= 100
                || (mySubmarine.possibleLocation.size() <= 30 && possibilitiesAddedIfSilence >= 40)
                || mySubmarine.possibleLocation.size() <= 10
                || mySubmarine.spotted;
    }

    private int computeDistance(Direction direction) {
        List<Coordinate> bestPath = Game.getInstance().bestPath;
        List<Coordinate> nextPath = IntStream.range(Game.getInstance().step, Game.getInstance().step + 3)
                .filter(value -> value < bestPath.size())
                .mapToObj(bestPath::get)
                .collect(Collectors.toList());

        Coordinate nextZone = nextPath.get(0);
        int maxDistance = 0;
        for (Coordinate coordinate : nextPath) {
            switch (direction) {
                case E: case W:
                    if (coordinate.y != nextZone.y)
                        return maxDistance;
                    maxDistance++;
                    break;
                case N: case S:
                    if (coordinate.x != nextZone.x)
                        return maxDistance;
                    maxDistance++;
                    break;
            }
        }
        return ThreadLocalRandom.current().nextInt(0, maxDistance + 1);
    }

    private long computePossibilitiesAddedIfSilence() {
        return Game.getInstance().mySubmarine.possibleLocation.stream()
                .mapToInt(this::emptyNeighbors)
                .sum();
    }

    private int emptyNeighbors(PossibleLocation coordinate) {
        int countEmpty = 0;
        for (Direction direction : Direction.values()) {
            for (int i = 1; i <= 4; i++) {
                PossibleLocation location = new PossibleLocation(direction.toCoordinate(coordinate));
                if (!location.isValid())
                    break;
                location.copyHistories(coordinate.histories);
                if (location.alreadyVisited())
                    break;
                countEmpty++;
            }
        }
        return countEmpty;
    }
}
