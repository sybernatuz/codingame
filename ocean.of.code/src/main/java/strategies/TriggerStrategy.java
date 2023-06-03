package strategies;

import objects.*;
import objects.actions.Action;
import objects.actions.Type;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TriggerStrategy {

    private static final TriggerStrategy INSTANCE = new TriggerStrategy();

    public static TriggerStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Grid.getInstance().mined.size() == 0) {
            return Optional.empty();
        }
        List<Mine> minesActive = Grid.getInstance().mined.stream()
                .filter(mine -> mine.active)
                .collect(Collectors.toList());

        if (Game.getInstance().enemySubmarine.coordinate != null) {
            return findMineCloseToEnemy(minesActive);
        }
        if (Game.getInstance().enemySubmarine.getDistinctPossibleLocation().size() <= 4) {
            return findMineToTrigger(minesActive);
        }
        return findMineWithFiveOrMoreEnemyLocations(minesActive);
    }

    private Optional<Action> findMineCloseToEnemy(List<Mine> minesActive) {
        PossibleLocation enemy = Game.getInstance().enemySubmarine.coordinate;
        Optional<Mine> mineOnEnemy = minesActive.stream()
                    .filter(enemy::equals)
                    .findFirst();

        if (mineOnEnemy.isPresent())
            return mineOnEnemy.map(this::createMine);

        return minesActive.stream()
                .filter(mine -> mine.isNeighbor(Game.getInstance().enemySubmarine.coordinate))
                .findFirst()
                .map(this::createMine);
    }

    private Optional<Action> findMineWithFiveOrMoreEnemyLocations(List<Mine> minesActive) {
        for (Mine mine : minesActive) {
            long possibleNeighbors = Game.getInstance().enemySubmarine.getDistinctPossibleLocation().stream()
                    .filter(mine::isNeighbor)
                    .count();
            if (possibleNeighbors >= 5 && iAmNotOnIt(mine)) {
                return Optional.of(createMine(mine));
            }
        }
        return Optional.empty();
    }

    private Optional<Action> findMineToTrigger(List<Mine> minesActive) {
        return minesActive.stream()
                .filter(coordinate -> mineCloseToPossibleEnemyLocation(coordinate) >= 1)
                .max(Comparator.comparing(this::mineCloseToPossibleEnemyLocation))
                .filter(this::iAmNotOnIt)
                .map(this::createMine);
    }

    private long mineCloseToPossibleEnemyLocation(Mine mineLocation) {
        return Game.getInstance().enemySubmarine.getDistinctPossibleLocation().stream()
                .filter(mineLocation::isNeighbor)
                .count();
    }
    private boolean iAmNotOnIt(Mine mineLocation) {
        return !Game.getInstance().mySubmarine.coordinateFinal.isNeighbor(mineLocation);
    }

    private Action createMine(Mine mine) {
        Grid.getInstance().mined.remove(mine);

        Action action = new Action();
        action.type = Type.TRIGGER;
        action.coordinate = mine;
        return action;
    }
}
