package strategies;

import objects.Coordinate;
import objects.Game;
import objects.Grid;
import objects.Mine;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;

import java.util.Optional;

public class MineStrategy {

    private static final MineStrategy INSTANCE = new MineStrategy();

    public static MineStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Game.getInstance().mineCooldown > 0) {
            return Optional.empty();
        }

        return findZoneToMine().map(this::createMine);
    }

    private Optional<Coordinate> findZoneToMine() {
        return Grid.getInstance().empty.stream()
                .filter(coordinate -> Game.getInstance().mySubmarine.coordinateFinal.computeDistance(coordinate) == 1)
                .findFirst();
    }

    private Action createMine(Coordinate zone) {
        Grid.getInstance().mined.add(new Mine(zone));

        Action action = new Action();
        action.type = Type.MINE;
        action.direction = Direction.getDirection(Game.getInstance().mySubmarine.coordinateFinal, zone);
        return action;
    }
}
