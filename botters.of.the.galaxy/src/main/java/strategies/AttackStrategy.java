package strategies;

import enums.TeamEnum;
import enums.UnitTypeEnum;
import objects.Entity;
import objects.Game;
import utils.ComputeUtils;
import utils.SearchUtils;

import java.util.List;
import java.util.Optional;

public class AttackStrategy {

    public String process(Game game) {
        Entity enemyHero = SearchUtils.findHero(game.entities, TeamEnum.ENEMY);

        Optional<Entity> lastHit = findForLastHit(game.entities, game.currentHero.entity);
        if (lastHit.isPresent()) {
            return lastHit.map(entity -> String.format("ATTACK %s", entity.unitId)).get();
        }

        if (enemyHero != null) {
            double distanceBetweenHeroes = ComputeUtils.computeDistance(game.currentHero.entity.coordinate, enemyHero.coordinate);
            if (distanceBetweenHeroes < game.currentHero.entity.attackRange) {
                return "ATTACK_NEAREST " + UnitTypeEnum.HERO;
            }
        }

        return "ATTACK_NEAREST " + UnitTypeEnum.UNIT;
    }

    public Optional<Entity> findForLastHit(List<Entity> entities, Entity myHero) {
        return entities.stream()
                .filter(entity -> entity.unitType.equals(UnitTypeEnum.UNIT))
                .filter(entity -> entity.health <= myHero.attackDamage)
                .findFirst();
    }


}
