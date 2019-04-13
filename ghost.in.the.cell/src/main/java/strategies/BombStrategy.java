package strategies;

import enums.OwnerEnum;
import objects.Attack;
import objects.Factory;
import objects.Troop;

import java.util.List;

public class BombStrategy {

    public Attack computeBomb(List<Factory> factories, List<Troop> troops) {
        return factories.stream()
                .filter(factory -> factory.owner.equals(OwnerEnum.ENEMY))
                .map(factory -> {
                    Attack attack = new Attack();
                    attack.source = factories.stream()
                            .filter(factory1 -> factory1.owner.equals(OwnerEnum.FRIEND))
                            .findFirst()
                            .orElse(null);
                    attack.target = factory;
                    return attack;
                })
                .findFirst()
                .orElse(null);
    }
}
