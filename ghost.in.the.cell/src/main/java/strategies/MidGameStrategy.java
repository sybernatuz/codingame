package strategies;

import game.Game;
import objects.Factory;
import objects.Side;

import java.util.List;
import java.util.stream.Collectors;

class MidGameStrategy extends FarmStrategy {

    private static final MidGameStrategy INSTANCE = new MidGameStrategy();

    public static MidGameStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<Factory> factoriesToTarget() {
        return Game.getInstance().factories.stream()
                .filter(factory -> !Side.ENEMY_SIDE.equals(factory.side))
                .collect(Collectors.toList());
    }
}
