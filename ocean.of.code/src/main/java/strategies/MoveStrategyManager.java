package strategies;

import objects.Coordinate;
import objects.Game;
import objects.actions.Action;
import objects.actions.Type;
import pathfinder.MaxEmptyPathFinder;

import java.util.Collections;
import java.util.List;

public class MoveStrategyManager {

    private static final MoveStrategyManager INSTANCE = new MoveStrategyManager();

    public static MoveStrategyManager getInstance() {
        return INSTANCE;
    }

    public Action process() {
        Action move = new Action();
        if (Game.getInstance().step >= Game.getInstance().bestPath.size()) {
            List<Coordinate> nextPath = MaxEmptyPathFinder.getInstance().findMaxEmptyPath(Game.getInstance().mySubmarine.coordinateFinal, Game.getInstance().bestPath);
            nextPath.remove(Game.getInstance().mySubmarine.coordinateFinal);
            if (nextPath.isEmpty()) {
                move.type = Type.SURFACE;
                Collections.reverse(Game.getInstance().bestPath);
                Game.getInstance().step = 1;
                return move;
            }
            Game.getInstance().bestPath.addAll(nextPath);
        }

        return SilenceStrategy.getInstance().process()
                .orElseGet(() -> MoveStrategy.getInstance().process());
    }
}
