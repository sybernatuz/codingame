package strategies;

import enums.OwnerEnum;
import game.Game;
import objects.Action;
import objects.Factory;
import objects.Link;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class BombStrategy {

    private static final BombStrategy INSTANCE = new BombStrategy();

    public static BombStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> computeBomb() {
        if (Game.getInstance().availableBombs == 0)
            return Optional.empty();

        if (Game.getInstance().turn == 1) {
            Factory initialEnemyBase = Game.getInstance().getByOwner(OwnerEnum.ENEMY).get(0);
            if (initialEnemyBase.production == 0)
                return Optional.empty();

            return Optional.of(bombTheInitialEnemyBase());
        }
        boolean isFriendlyBombOnTheBoard = Game.getInstance().bombs.stream()
                .anyMatch(bomb -> OwnerEnum.FRIEND.equals(bomb.owner));
        if (isFriendlyBombOnTheBoard)
            return Optional.empty();

        return getByOptimalTarget();
    }

    private Optional<Action> getByOptimalTarget() {
        List<Factory> enemyFactories = Game.getInstance().getByOwner(OwnerEnum.ENEMY);

        for (Factory enemyFactory : enemyFactories) {
            if (enemyFactory.production < 3)
                continue;

            Optional<Link> closestFriendFactory = findClosestFactory(enemyFactory);
            if (!closestFriendFactory.isPresent())
                continue;

            boolean isTroopCloserThanFriendFactory = isTroopCloserThanFriendFactory(enemyFactory, closestFriendFactory.get());
            if (isTroopCloserThanFriendFactory)
                continue;

            Action action = new Action(Action.Type.BOMB);
            action.source = closestFriendFactory.get().neighbour;
            action.target = enemyFactory;

            Game.getInstance().availableBombs--;
            return Optional.of(action);
        }
        return Optional.empty();
    }

    private Optional<Link> findClosestFactory(Factory enemyFactory) {
        return enemyFactory.neighbours.stream()
                .filter(link -> OwnerEnum.FRIEND.equals(link.neighbour.owner))
                .min(Comparator.comparing(link -> link.distance));
    }

    private boolean isTroopCloserThanFriendFactory(Factory enemyFactory, Link closestFactory) {
        return Game.getInstance().troops.stream()
                .filter(troop -> troop.factoryTargetId == enemyFactory.id)
                .filter(troop -> troop.owner.equals(OwnerEnum.FRIEND))
                .anyMatch(troop -> troop.turnsToArrive < closestFactory.distance);
    }

    private Action bombTheInitialEnemyBase() {
        Factory initialBase = Game.getInstance().getByOwner(OwnerEnum.FRIEND).get(0);
        Factory initialEnemyBase = Game.getInstance().getByOwner(OwnerEnum.ENEMY).get(0);
        Action bomb = new Action(Action.Type.BOMB);
        bomb.source = initialBase;
        bomb.target = initialEnemyBase;

        Game.getInstance().availableBombs--;
        return bomb;
    }
}
