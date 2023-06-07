package strategies;

import game.Game;
import objects.Action;
import objects.Side;

import java.util.ArrayList;
import java.util.List;

public class StrategiesManager {

    private static final StrategiesManager INSTANCE = new StrategiesManager();

    public static StrategiesManager getInstance() {
        return INSTANCE;
    }

    public List<Action> process() {
        List<Action> actions = moves();
        BombStrategy.getInstance().computeBomb().ifPresent(actions::add);
        return actions;
    }

    private List<Action> moves() {
        if (Game.getInstance().turn == 1) {
            return FirstTurnStrategy.getInstance().process();
        }
//        boolean allInitialProductionFactoriesUpgraded = Game.getInstance().factories.stream()
//                .filter(factory -> !Side.ENEMY_SIDE.equals(factory.side))
//                .filter(factory -> factory.initialProduction > 0)
//                .allMatch(factory -> factory.production == 3);
//
//        if(!allInitialProductionFactoriesUpgraded) {
//            System.err.println("Early game");
//            return EarlyGameStrategy.getInstance().computeMoves();
//        }

        boolean allUpgraded = Game.getInstance().factories.stream()
                .filter(factory -> !Side.ENEMY_SIDE.equals(factory.side))
                .allMatch(factory -> factory.production == 3);

        boolean hasDoubleTroopsThanEnemy = Game.getInstance().myTroopsNumber >= Game.getInstance().enemyTroopsNumber * 2;

        if (!allUpgraded || !hasDoubleTroopsThanEnemy) {
            System.err.println("Mid game");
            List<Action> actions = new ArrayList<>();
            actions.addAll(MidGameStrategy.getInstance().computeMoves());
            actions.addAll(MoveForwardStrategy.getInstance().compute());
            return actions;
        }

        System.err.println("End game");
        List<Action> actions = new ArrayList<>();
        actions.addAll(EndGameStrategy.getInstance().computeMoves());
        actions.addAll(MoveForwardStrategy.getInstance().compute());
        return actions;
    }
}
