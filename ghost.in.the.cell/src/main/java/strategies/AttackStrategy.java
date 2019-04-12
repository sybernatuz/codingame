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
                .forEach(friendFactory -> addAttacksByFriendFactory(attacks, friendFactory, factories));
        return attacks;
    }

    private void addAttacksByFriendFactory(List<Attack> attacks, Factory friendFactory, List<Factory> factories) {
        if (friendFactory.troopNumber <= 5)
            return;
        List<Link> possibleTargets = friendFactory.neighbours.stream()
                .filter(link -> !link.neighbour.owner.equals(OwnerEnum.FRIEND))
                .collect(Collectors.toList());

        Integer troopNumber = friendFactory.troopNumber;
        if (possibleTargets.isEmpty()) {
            Path pathToFriendBaseInDanger = GraphUtils.findPathToFriendFactoryInDanger(friendFactory);
            if (pathToFriendBaseInDanger == null || pathToFriendBaseInDanger.factories == null || pathToFriendBaseInDanger.factories.isEmpty())
                return;
            Attack attack = new Attack();
            attack.source = friendFactory;
            attack.target = pathToFriendBaseInDanger.factories.get(0);
            attack.number = friendFactory.troopNumber - 1;
            attacks.add(attack);
        }
        Link target = getByBestScoreDistanceProduction(possibleTargets)
                .orElse(null);

        if (target == null)
            return;

        Attack attack = new Attack();
        attack.source = friendFactory;
        attack.target = target.neighbour;
        attack.number = computeTroopNumberToMoves(target, friendFactory);
        attacks.add(attack);
    }

    private Optional<Link> getByBestScoreDistanceProduction(List<Link> possibleTargets) {
        return possibleTargets.stream()
                .min(Comparator.comparing(link -> link.distance / (link.neighbour.production + 1)));
    }

    private Integer computeTroopNumberToMoves(Link target, Factory friendFactory) {
        Integer number = target.neighbour.troopNumber + target.distance + 1;
        if (number < friendFactory.troopNumber)
            return number;
        return friendFactory.troopNumber - 2;
    }
}
