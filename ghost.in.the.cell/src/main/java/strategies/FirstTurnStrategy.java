package strategies;

import enums.OwnerEnum;
import game.Game;
import objects.Action;
import objects.Factory;
import objects.Side;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class FirstTurnStrategy {

    private static final FirstTurnStrategy INSTANCE = new FirstTurnStrategy();

    public static FirstTurnStrategy getInstance() {
        return INSTANCE;
    }

    public List<Action> process() {
        Factory initialBase = Game.getInstance().getByOwner(OwnerEnum.FRIEND).get(0);
        return splitToClosestProductionFactories(initialBase);
    }

    private List<Action> splitToClosestProductionFactories(Factory initialBase) {
        List<Action> actions = goToCloseProductionFactories(initialBase);
        if (initialBase.troopNumber >= 10) {
            Action upgrade = new Action(Action.Type.INC);
            upgrade.source = initialBase;
            actions.add(upgrade);
        }
        return actions;
    }

    private List<Action> goToCloseProductionFactories(Factory initialBase) {
        List<Factory> firstZonesToRush = findZoneWithProductionToRush(initialBase);
        List<Action> actions = new ArrayList<>();
        for (Factory zoneToRush : firstZonesToRush) {
            if (zoneToRush.troopNumber < initialBase.troopNumber) {
                Action action = Action.MoveBuilder.of(initialBase)
                        .withTarget(zoneToRush)
                        .withNumber(zoneToRush.troopNumber + 1)
                        .build();
                actions.add(action);
            }
        }
        return actions;
    }

    private List<Factory> findZoneWithProductionToRush(Factory initialBase) {
        return Game.getInstance().factories.stream()
                .filter(factory -> !Side.ENEMY_SIDE.equals(factory.side))
                .filter(factory -> OwnerEnum.NONE.equals(factory.owner))
                .filter(factory -> factory.production > 0)
                .sorted(Comparator.comparing(factory -> computeDistanceProductionRatio(initialBase, factory)))
                .collect(Collectors.toList());
    }

    private int computeDistanceProductionRatio(Factory initialBase, Factory factory) {
        return factory.neighbours.stream()
                .filter(link -> link.neighbour.equals(initialBase))
                .mapToInt(link -> link.distance / (factory.production))
                .min()
                .orElse(Integer.MAX_VALUE);
    }
}
