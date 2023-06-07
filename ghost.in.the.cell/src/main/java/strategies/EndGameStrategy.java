package strategies;

import game.Game;
import objects.Factory;

import java.util.List;

public class EndGameStrategy extends FarmStrategy{

    private static final EndGameStrategy INSTANCE = new EndGameStrategy();

    public static EndGameStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    protected List<Factory> factoriesToTarget() {
        return Game.getInstance().factories;
    }
}
