package strategies;

import objects.Coordinate;
import objects.Game;
import objects.Grid;
import objects.actions.Action;
import objects.actions.Type;

import java.util.Optional;

public class TriggerStrategy {

    private static final TriggerStrategy INSTANCE = new TriggerStrategy();

    public static TriggerStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Grid.getInstance().mined.size() == 0) {
            return Optional.empty();
        }
        if (Game.getInstance().enemySubmarine.possibleLocation.size() < 4) {
            return findMineToTrigger();
        }
        return findMineWith5OrMoreEnemyLocations();
    }

    private Optional<Action> findMineWith5OrMoreEnemyLocations() {
        for (Coordinate mine : Grid.getInstance().mined) {
            long possibleNeighbors = Game.getInstance().enemySubmarine.possibleLocation.stream()
                    .filter(mine::isNeighbor)
                    .count();
            if (possibleNeighbors >= 5) {
                return Optional.of(createMine(mine));
            }
        }
        return Optional.empty();
    }

    private Optional<Action> findMineToTrigger() {
        return Grid.getInstance().mined.stream()
                .filter(this::isMineCloseToPossibleEnemyLocation)
                .filter(this::iAmNotOnIt)
                .findFirst()
                .map(this::createMine);
    }

    private boolean isMineCloseToPossibleEnemyLocation(Coordinate mineLocation) {
        return Game.getInstance().enemySubmarine.possibleLocation.stream()
                .anyMatch(mineLocation::isNeighbor);

    }
    private boolean iAmNotOnIt(Coordinate mineLocation) {
        return !Game.getInstance().mySubmarine.coordinateFinal.isNeighbor(mineLocation);
    }

    private Action createMine(Coordinate mine) {
        Grid.getInstance().mined.remove(mine);

        Action action = new Action();
        action.type = Type.TRIGGER;
        action.coordinate = mine;
        return action;
    }
}
