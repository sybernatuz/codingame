package strategies;

import enums.OwnerEnum;
import objects.Attack;
import objects.Bomb;
import objects.Factory;
import objects.Link;
import objects.Troop;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BombStrategy {

    public Attack computeBomb(List<Factory> factories, List<Troop> troops, List<Bomb> bombs) {
        return getByFirstAction(factories, bombs)
                .orElse(getByOptimalTarget(factories, troops, bombs)
                .orElse(null));
    }

    private Optional<Attack> getByFirstAction(List<Factory> factories, List<Bomb> bombs) {
        List<Factory> friendFactories = factories.stream()
                .filter(factory -> factory.owner.equals(OwnerEnum.FRIEND))
                .collect(Collectors.toList());
        List<Factory> enemyFactories = factories.stream()
                .filter(factory -> factory.owner.equals(OwnerEnum.ENEMY))
                .collect(Collectors.toList());
        Optional<Bomb> friendBomb = bombs.stream()
                .filter(bomb -> bomb.owner.equals(OwnerEnum.FRIEND))
                .findFirst();
        boolean isFirstAction = enemyFactories.size() == 1 && friendFactories.size() == 1 && friendBomb.isEmpty();
        if (!isFirstAction)
            return Optional.empty();

        Attack attack = new Attack();
        attack.source = friendFactories.get(0);
        attack.target = enemyFactories.get(0);
        return Optional.of(attack);
    }

    private Optional<Attack> getByOptimalTarget(List<Factory> factories, List<Troop> troops, List<Bomb> bombs) {
        List<Factory> enemyFactoryWithFriendNeighbour = findEnemyFactoriesWithFriendNeighbour(factories);

        for (Factory enemyFactory : enemyFactoryWithFriendNeighbour) {
            if (enemyFactory.troopNumber < 10)
                continue;

            boolean isFriendBombTargetedThisFactory = isFriendBombTargetedThisFactory(bombs, enemyFactory);
            if (isFriendBombTargetedThisFactory)
                continue;

            List<Link> friendNeighbours = findFriendNeighbours(enemyFactory);
            Link closestFactory = findClosestFactory(enemyFactory);
            if (closestFactory == null || !friendNeighbours.contains(closestFactory))
                continue;

            boolean isTroopCloserThanFriendFactory = isTroopCloserThanFriendFactory(troops, enemyFactory, closestFactory);
            if (isTroopCloserThanFriendFactory)
                continue;

            Attack attack = new Attack();
            attack.target = enemyFactory;
            attack.source = closestFactory.neighbour;
            return Optional.of(attack);
        }
        return Optional.empty();
    }

    private List<Factory> findEnemyFactoriesWithFriendNeighbour(List<Factory> factories) {
        return factories.stream()
                .filter(factory -> factory.owner.equals(OwnerEnum.ENEMY))
                .filter(factory -> factory.neighbours.stream()
                        .map(link -> link.neighbour)
                        .anyMatch(neighbour -> neighbour.owner.equals(OwnerEnum.FRIEND)))
                .collect(Collectors.toList());
    }

    private boolean isFriendBombTargetedThisFactory(List<Bomb> bombs, Factory enemyFactory) {
        return bombs.stream()
                .filter(bomb -> bomb.owner.equals(OwnerEnum.FRIEND))
                .anyMatch(bomb -> bomb.factoryTargetId == enemyFactory.id);
    }

    private List<Link> findFriendNeighbours(Factory enemyFactory) {
        return enemyFactory.neighbours.stream()
                .filter(link -> link.neighbour.owner.equals(OwnerEnum.FRIEND))
                .collect(Collectors.toList());
    }

    private Link findClosestFactory(Factory enemyFactory) {
        return enemyFactory.neighbours.stream()
                .min(Comparator.comparing(link -> link.distance))
                .orElse(null);
    }

    private boolean isTroopCloserThanFriendFactory(List<Troop> troops, Factory enemyFactory, Link closestFactory) {
        return troops.stream()
                .filter(troop -> troop.factoryTargetId == enemyFactory.id)
                .filter(troop -> troop.owner.equals(OwnerEnum.FRIEND))
                .anyMatch(troop -> troop.turnsToArrive < closestFactory.distance);
    }
}
