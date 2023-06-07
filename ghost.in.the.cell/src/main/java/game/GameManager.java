package game;

import enums.OwnerEnum;
import objects.Factory;
import pathfinder.PathFinder;

import java.util.List;
import java.util.Scanner;

public class GameManager {

    private static final GameManager INSTANCE = new GameManager();

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public void initialize(Scanner in) {
        LinkInitializer.getInstance().initialize(in);
    }

    public void update(Scanner in) {
        Game.getInstance().reset();
        GameUpdater.getInstance().update(in);
        if (Game.getInstance().turn == 1) {
            SideInitializer.getInstance().initialize();
            Game.getInstance().pathToFollow = PathFinder.getInstance().getPathToContestedFactory();
            log();
        }
    }

    private void log() {
        System.err.println("--- FACTORIES ---");
        Game.getInstance().factories.forEach(System.err::println);
        System.err.println("---");
        System.err.println("--- Distances ---");
        Game.getInstance().getByOwner(OwnerEnum.FRIEND)
                .get(0)
                .neighbours
                .forEach(System.err::println);
        System.err.println("---");
        System.err.println("--- Path ---");
        Game.getInstance().pathToFollow.forEach(System.err::println);
        System.err.println("---");
    }
}
