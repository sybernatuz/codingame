package strategies;

import enums.OwnerEnum;
import game.Game;
import objects.Action;
import objects.Factory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

abstract class FarmStrategy {

    private static final int UPGRADE_COST = 10;
    private static final int TROOPS_NUMBER_TO_DEFEND = 5;

    public List<Action> computeMoves() {
        List<Action> actions = new ArrayList<>();
        actions.addAll(takeNotOwned());
        actions.addAll(reinforcementsOwned());
        actions.addAll(sendForUpgrade());
        actions.addAll(upgrade());

        return actions;
    }

    private List<Action> takeNotOwned() {
        List<Factory> factoriesToTarget = factoriesToTarget();
        List<Factory> factoriesNotOwned = factoriesToTarget.stream()
                .filter(factory -> !OwnerEnum.FRIEND.equals(factory.owner))
                .collect(Collectors.toList());

        List<Action> actions = new ArrayList<>();
        for (Factory factoryNotOwned : factoriesNotOwned) {
            int troopsNeededToBeTaken = factoryNotOwned.troopNumber + 1;
            System.err.println("No " + factoryNotOwned.id);
            troopsNeededToBeTaken += Game.getInstance().getNumberOfTroopsGoing(factoryNotOwned, OwnerEnum.ENEMY);
            actions.addAll(sendTroops(factoryNotOwned, troopsNeededToBeTaken));
        }
        return actions;
    }

    private List<Action> reinforcementsOwned() {
        List<Factory> factoriesOwned = Game.getInstance().getByOwner(OwnerEnum.FRIEND);

        List<Action> actions = new ArrayList<>();
        for (Factory factoryOwned : factoriesOwned) {
            int troopNumber = computeAvailableTroopToMove(factoryOwned);
            if (troopNumber >= 0)
                continue;
            int reinforcementsNeeded = troopNumber * -1;
            actions.addAll(sendTroops(factoryOwned, reinforcementsNeeded));
        }
        return actions;
    }

    private List<Action> sendForUpgrade() {
        List<Factory> factoriesOwned = Game.getInstance().getByOwner(OwnerEnum.FRIEND);
        List<Factory> factoriesOwnedNeedUpgrade = factoriesOwned.stream()
                .filter(factory -> factory.production < 3)
                .collect(Collectors.toList());

        List<Action> actions = new ArrayList<>();
        for (Factory factoryOwnedNeedUpgrade : factoriesOwnedNeedUpgrade) {
            factoriesOwned.stream()
                    .filter(factory -> factory.production == 3)
                    .filter(factory -> computeAvailableTroopToMove(factory) > TROOPS_NUMBER_TO_DEFEND)
                    .findFirst()
                    .ifPresent(canSend -> {
                        int canSendValue = computeAvailableTroopToMove(canSend) - TROOPS_NUMBER_TO_DEFEND;
                        Action action = Action.MoveBuilder.of(canSend)
                                        .withTarget(factoryOwnedNeedUpgrade)
                                        .withNumber(canSendValue)
                                        .build();
                        actions.add(action);
                    });
        }
        return actions;
    }

    private List<Action> upgrade() {
        return Game.getInstance().getByOwner(OwnerEnum.FRIEND).stream()
                .filter(factory -> factory.production < 3)
                .filter(factory -> computeAvailableTroopToMove(factory) >= UPGRADE_COST)
                .map(factory -> {
                    Action action = new Action(Action.Type.INC);
                    action.source = factory;
                    factory.troopNumber -= UPGRADE_COST;
                    return action;
                })
                .collect(Collectors.toList());
    }

    private int computeAvailableTroopToMove(Factory factory) {
        int numberOfEnemyAttackers = Game.getInstance().getNumberOfTroopsGoing(factory, OwnerEnum.ENEMY);
        return factory.troopNumber - numberOfEnemyAttackers;
    }

    private List<Factory> findFriendNeighbors(Factory factoryNotOwned) {
        return factoryNotOwned.neighbours.stream()
                .filter(link -> OwnerEnum.FRIEND.equals(link.neighbour.owner))
                .filter(link -> computeAvailableTroopToMove(link.neighbour) > 0)
                .sorted(Comparator.comparing(link -> link.distance))
                .map(link -> link.neighbour)
                .collect(Collectors.toList());
    }

    private List<Action> sendTroops(Factory factory, int troopsNeeded) {
        List<Action> sendTroops = new ArrayList<>();

        List<Factory> friendNeighbors = findFriendNeighbors(factory);
        int troopsAlreadyGoing = Game.getInstance().getNumberOfTroopsGoing(factory, OwnerEnum.FRIEND);
        troopsNeeded = troopsNeeded - troopsAlreadyGoing;
        if (OwnerEnum.ENEMY.equals(factory.owner))
            troopsNeeded += factory.production * 3 + 3;

        for (Factory friendNeighbor : friendNeighbors) {
            if (troopsNeeded <= 0)
                return sendTroops;

            int troopsAvailable = computeAvailableTroopToMove(friendNeighbor);
            int troopsToSend = Math.min(troopsAvailable, troopsNeeded);

            Action action = Action.MoveBuilder.of(friendNeighbor)
                            .withTarget(factory)
                            .withNumber(troopsToSend)
                            .build();
            sendTroops.add(action);
            troopsNeeded -= troopsToSend;
        }
        return sendTroops;
    }

    protected abstract List<Factory> factoriesToTarget();
}
