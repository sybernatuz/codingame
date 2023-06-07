package game;

import enums.OwnerEnum;
import objects.Bomb;
import objects.Factory;
import objects.Side;
import objects.Troop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private static final Game INSTANCE = new Game();

    public List<Factory> factories = new ArrayList<>();
    public List<Troop> troops = new ArrayList<>();
    public List<Bomb> bombs = new ArrayList<>();
    public int turn = 0;

    public int myTroopsNumber = 0;
    public int enemyTroopsNumber = 0;
    public List<Factory> pathToFollow = new ArrayList<>();
    public int availableBombs = 2;

    public static Game getInstance() {
        return INSTANCE;
    }

    public void reset() {
        turn++;
        troops.clear();
        bombs.clear();
    }

    public List<Factory> getByOwner(OwnerEnum owner) {
        return factories.stream()
                .filter(factory -> owner.equals(factory.owner))
                .collect(Collectors.toList());
    }

    public List<Factory> getBySide(Side side) {
        return factories.stream()
                .filter(factory -> side.equals(factory.side))
                .collect(Collectors.toList());
    }

    public int getNumberOfTroopsGoing(Factory factory, OwnerEnum ownerEnum) {
        return troops.stream()
                .filter(troop -> ownerEnum.equals(troop.owner))
                .filter(troop -> troop.factoryTargetId ==  factory.id)
                .mapToInt(troop -> troop.number)
                .sum();
    }
}
