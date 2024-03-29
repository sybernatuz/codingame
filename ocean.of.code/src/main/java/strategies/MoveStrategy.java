package strategies;

import objects.Coordinate;
import objects.Game;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;

import java.util.List;

class MoveStrategy {

    private static final MoveStrategy INSTANCE = new MoveStrategy();

    static MoveStrategy getInstance() {
        return INSTANCE;
    }

    public Action process(List<Action> actions) {
        Action action = new Action();
        Coordinate zoneToGo = Game.getInstance().bestPath.get(Game.getInstance().step);
        action.type = Type.MOVE;
        action.direction = Direction.getDirection(Game.getInstance().mySubmarine.coordinateFinal, zoneToGo);
        action.powerCharge = PowerChargeStrategy.getInstance().getPowerToCharge(actions);
        Game.getInstance().step++;
        return action;
    }

}
