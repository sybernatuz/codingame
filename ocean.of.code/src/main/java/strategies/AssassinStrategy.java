package strategies;

import objects.Coordinate;
import objects.Game;
import objects.PossibleLocation;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssassinStrategy {

    private static final AssassinStrategy INSTANCE = new AssassinStrategy();

    public static AssassinStrategy getInstance() {
        return INSTANCE;
    }

    public List<Action> process() {
        if (!canAssassin())
            return Collections.emptyList();

        PossibleLocation enemyLocation = Game.getInstance().enemySubmarine.coordinate;
        Coordinate myLocation = Game.getInstance().mySubmarine.coordinateFinal;
        int distance = enemyLocation.computeDistance(myLocation);

        if (distance != 5)
            return Collections.emptyList();

        return findDirectionsToBeCloser(enemyLocation, myLocation).stream()
                .filter(this::canMoveHere)
                .findFirst()
                .map(this::actionsToAssassin)
                .orElse(Collections.emptyList());
    }

    private List<Action> actionsToAssassin(Direction direction) {
        Game.getInstance().bestPath.subList(Game.getInstance().step, Game.getInstance().bestPath.size()).clear();
        Game.getInstance().bestPath.add(direction.toCoordinate(Game.getInstance().mySubmarine.coordinateFinal));
        Game.getInstance().step++;

        List<Action> actions = new ArrayList<>();
        Action move = new Action();
        move.type = Type.MOVE;
        move.direction = direction;
        move.powerCharge = PowerChargeStrategy.getInstance().getPowerToCharge();
        actions.add(move);

        Action torpedo = new Action();
        torpedo.type = Type.TORPEDO;
        torpedo.coordinate = Game.getInstance().enemySubmarine.coordinate;
        actions.add(torpedo);
        return actions;
    }

    private List<Direction> findDirectionsToBeCloser(Coordinate source, Coordinate target) {
        List<Direction> possibleDirections = new ArrayList<>();
        if (target.x > source.x)
            possibleDirections.add(Direction.E);
        if (target.x < source.x)
            possibleDirections.add(Direction.W);
        if (target.y > source.y)
            possibleDirections.add(Direction.S);
        if (target.y > source.y)
            possibleDirections.add(Direction.N);

        return possibleDirections;
    }

    private boolean canMoveHere(Direction direction) {
        Coordinate toGo = direction.toCoordinate(Game.getInstance().mySubmarine.coordinateFinal);
        return toGo.isValid() && !Game.getInstance().bestPath.contains(toGo);
    }

    private boolean canAssassin() {
        return Game.getInstance().enemySubmarine.coordinate != null
                && Game.getInstance().torpedoCooldown <= 1;
    }
}
