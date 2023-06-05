package strategies;

import objects.Coordinate;
import objects.Game;
import objects.actions.Action;
import objects.actions.Type;
import pathfinder.MaxEmptyPathFinder;

import java.util.List;

public class MoveStrategyManager {

    private static final MoveStrategyManager INSTANCE = new MoveStrategyManager();

    public static MoveStrategyManager getInstance() {
        return INSTANCE;
    }

    public Action process(List<Action> actions) {
        Action move = new Action();
        if (Game.getInstance().step >= Game.getInstance().bestPath.size()) {
            System.err.println("Searching for new path ...");
            List<Coordinate> nextPath = MaxEmptyPathFinder.getInstance().findMaxEmptyPath(Game.getInstance().mySubmarine.coordinateFinal, Game.getInstance().bestPath);
            nextPath.remove(Game.getInstance().mySubmarine.coordinateFinal);
            System.err.println("New path : " + nextPath.size());
            Game.getInstance().bestPath.addAll(nextPath);
            if (nextPath.isEmpty()) {
                move.type = Type.SURFACE;
                Game.getInstance().bestPath.clear();
                Game.getInstance().step = 0;
                return move;
            }
        }

        return SilenceStrategy.getInstance().process()
                .orElseGet(() -> MoveStrategy.getInstance().process(actions));
    }
}
