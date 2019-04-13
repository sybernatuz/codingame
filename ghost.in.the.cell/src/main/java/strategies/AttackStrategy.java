package strategies;


import enums.OwnerEnum;
import objects.Attack;
import objects.Factory;
import objects.Link;
import objects.Path;
import objects.Troop;
import utils.GraphUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttackStrategy {

    public List<Attack> computeAttacks(List<Factory> factories, List<Troop> troops) {
        List<Attack> attacks = new ArrayList<>();
        factories.stream()
                .filter(factory -> factory.owner.equals(OwnerEnum.FRIEND))
                .filter(factory -> troops.stream()
                        .filter(troop -> troop.owner.equals(OwnerEnum.ENEMY))
                        .filter(troop -> troop.factoryTargetId == factory.id)
                        .mapToInt(troop -> troop.number)
                        .sum() < factory.troopNumber
                )
                .forEach(friendFactory -> addAttacksByFriendFactory(attacks, friendFactory, factories));
        return attacks;
    }

    private void addAttacksByFriendFactory(List<Attack> attacks, Factory friendFactory, List<Factory> factories) {
        if (friendFactory.troopNumber <= 5)
            return;
        List<Link> possibleTargets = findNotFriendNeighbours(friendFactory);

        if (possibleTargets.isEmpty()) {
            Attack moveTroops = sendAllTroopsToTheFront(friendFactory, factories);
            if (moveTroops != null)
                attacks.add(moveTroops);
            return;
        }

        int troopNumber = friendFactory.troopNumber;
        while (troopNumber > 1) {
            Link target = getByBestScoreDistanceProduction(possibleTargets).orElse(null);

            if (target == null)
                return;

            Attack attack = new Attack();
            attack.source = friendFactory;
            attack.target = target.neighbour;
            attack.number = computeTroopNumberToMoves(target, friendFactory);
            attacks.add(attack);
            possibleTargets.remove(target);
            troopNumber -= attack.number;
        }
    }

    private List<Link> findNotFriendNeighbours(Factory friendFactory) {
        return friendFactory.neighbours.stream()
                .filter(link -> !link.neighbour.owner.equals(OwnerEnum.FRIEND))
                .collect(Collectors.toList());
    }

    private Attack sendAllTroopsToTheFront(Factory friendFactory, List<Factory> factories) {
        Path pathToFriendBaseInDanger = GraphUtils.findPathToFriendFactoryInDanger(friendFactory, factories);
        if (pathToFriendBaseInDanger == null || pathToFriendBaseInDanger.factories == null || pathToFriendBaseInDanger.factories.isEmpty())
            return null;
        Attack attack = new Attack();
        attack.source = friendFactory;
        attack.target = pathToFriendBaseInDanger.factories.get(0);
        attack.number = friendFactory.troopNumber - 1;
        return attack;
    }

    private Optional<Link> getByBestScoreDistanceProduction(List<Link> possibleTargets) {
        return possibleTargets.stream()
                .min(Comparator.comparing(link -> link.distance / (link.neighbour.production + 1)));
    }

    private Integer computeTroopNumberToMoves(Link target, Factory friendFactory) {
        int number = target.neighbour.troopNumber + target.distance + 1;
        if (target.neighbour.owner.equals(OwnerEnum.ENEMY))
            number += target.distance * target.neighbour.production;
        if (number < friendFactory.troopNumber)
            return number;
        return friendFactory.troopNumber - 2;
    }
}
