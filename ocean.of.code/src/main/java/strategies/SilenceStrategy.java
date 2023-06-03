package strategies;

import objects.Coordinate;
import objects.Game;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;

import java.util.ArrayList;
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
        return possibilitiesAddedIfSilence >= 150
                || (mySubmarine.possibleLocation.size() <= 30 && possibilitiesAddedIfSilence >= 40)
                || mySubmarine.possibleLocation.size() <= 10
                || mySubmarine.spotted;
    }

    private int computeDistance(Direction direction) {
        List<Coordinate> nextPath = IntStream.range(Game.getInstance().step, Game.getInstance().step + 3)
                .filter(value -> value < Game.getInstance().bestPath.size())
                .mapToObj(value -> Game.getInstance().bestPath.get(value))
                .collect(Collectors.toList());

        Coordinate nextZone = nextPath.get(0);
        int count = 0;
        for (Coordinate coordinate : nextPath) {
            switch (direction) {
                case E: case W:
                    if (coordinate.y != nextZone.y)
                        return count;
                    count++;
                    break;
                case N: case S:
                    if (coordinate.x != nextZone.x)
                        return count;
                    count++;
                    break;
            }
        }
        return ThreadLocalRandom.current().nextInt(0, count + 1);
    }

    private long computePossibilitiesAddedIfSilence() {
        return Game.getInstance().mySubmarine.possibleLocation.stream()
                .flatMap(possibleLocation -> emptyNeighbors(possibleLocation).stream())
                .distinct()
                .count();
    }

    private List<PossibleLocation> emptyNeighbors(PossibleLocation coordinate) {
        int[][] moves = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        List<PossibleLocation> countEmpty = new ArrayList<>();
        for (int[] move : moves) {
            for (int i = 1; i <= 4; i++) {
                PossibleLocation location = new PossibleLocation(coordinate);
                location.x += i * move[0];
                location.y += i * move[1];
                if (!location.isValid() || location.alreadyVisited()) {
                    break;
                }
                countEmpty.add(location);
            }
        }
        return countEmpty;
    }
}
