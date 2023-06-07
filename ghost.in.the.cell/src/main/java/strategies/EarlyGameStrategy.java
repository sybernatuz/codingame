package strategies;

import game.Game;
import objects.Factory;
import objects.Side;

import java.util.List;
import java.util.stream.Collectors;

class EarlyGameStrategy extends FarmStrategy {

    private static final EarlyGameStrategy INSTANCE = new EarlyGameStrategy();

    public static EarlyGameStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<Factory> factoriesToTarget() {
        return Game.getInstance().factories.stream()
                .filter(factory -> !Side.ENEMY_SIDE.equals(factory.side))
                .filter(factory -> factory.initialProduction > 0)
                .collect(Collectors.toList());
    }
}
