package game;

import enums.OwnerEnum;
import objects.Factory;
import objects.Side;

class SideInitializer {

    private static final SideInitializer INSTANCE = new SideInitializer();

    static SideInitializer getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        Game.getInstance().factories.stream()
                .filter(factory -> OwnerEnum.NONE.equals(factory.owner))
                .forEach(this::dispatchSide);

        Game.getInstance().factories.stream()
                .filter(factory -> OwnerEnum.FRIEND.equals(factory.owner))
                .forEach(factory -> factory.side = Side.MY_SIDE);
        Game.getInstance().factories.stream()
                .filter(factory -> OwnerEnum.ENEMY.equals(factory.owner))
                .forEach(factory -> factory.side = Side.ENEMY_SIDE);
    }

    private void dispatchSide(Factory factory) {
        int closestFriendFactoryDistance = findDistanceForOwner(factory, OwnerEnum.FRIEND);
        int closestEnemyFactoryDistance = findDistanceForOwner(factory, OwnerEnum.ENEMY);

        if (closestFriendFactoryDistance < closestEnemyFactoryDistance)
            factory.side = Side.MY_SIDE;
        if (closestFriendFactoryDistance > closestEnemyFactoryDistance)
            factory.side = Side.ENEMY_SIDE;
        if (closestFriendFactoryDistance == closestEnemyFactoryDistance)
            factory.side = Side.CONTESTED;
    }

    private int findDistanceForOwner(Factory source, OwnerEnum neighborOwner) {
        return source.neighbours.stream()
                .filter(link -> neighborOwner.equals(link.neighbour.owner))
                .map(link -> link.distance)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
