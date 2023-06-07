package strategies;


import enums.OwnerEnum;
import game.Game;
import objects.Action;
import objects.Direction;
import objects.Factory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class MoveForwardStrategy {

    private static final MoveForwardStrategy INSTANCE = new MoveForwardStrategy();

    public static MoveForwardStrategy getInstance() {
        return INSTANCE;
    }

    public List<Action> compute() {
        return Game.getInstance().getByOwner(OwnerEnum.FRIEND).stream()
                .filter(factory -> computeAvailableTroopToMove(factory) > 0)
                .filter(factory -> factory.production == 3)
                .map(this::moveForward)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Action> moveForward(Factory factory) {
        int availableTroops = computeAvailableTroopToMove(factory);
        Optional<Factory> toGoForward = getFactoryToGoForDirection(factory);
        if (!toGoForward.isPresent())
            return Optional.empty();

        Action action = new Action(Action.Type.MOVE);
        action.target = toGoForward.get();
        action.source = factory;
        action.number = availableTroops - 1;
        return Optional.of(action);
    }

    private Optional<Factory> getFactoryToGoForDirection(Factory source) {
        List<Factory> pathToFollow = Game.getInstance().pathToFollow;
        int currentIndex = pathToFollow.indexOf(source);

        int indexToGo = currentIndex + Direction.FORWARD.value;
        if (indexToGo >= pathToFollow.size())
            return Optional.empty();

        return Optional.of(pathToFollow.get(indexToGo));
    }

    private int computeAvailableTroopToMove(Factory factory) {
        int numberOfEnemyAttackers = Game.getInstance().getNumberOfTroopsGoing(factory, OwnerEnum.ENEMY);
        return factory.troopNumber - numberOfEnemyAttackers;
    }
}
