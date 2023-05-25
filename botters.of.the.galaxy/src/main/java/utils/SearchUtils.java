package utils;

import enums.HeroEnum;
import enums.TeamEnum;
import enums.UnitTypeEnum;
import objects.Entity;

import java.util.List;
import java.util.Optional;

public class SearchUtils {

    public static Entity findHero(List<Entity> entities, TeamEnum team) {
        return entities.stream()
                .filter(entity -> team.equals(entity.team))
                .filter(entity -> UnitTypeEnum.HERO.equals(entity.unitType))
                .findFirst()
                .orElse(null);
    }

    public static Optional<Entity> findMyHero(List<Entity> entities, HeroEnum heroEnum) {
        return entities.stream()
                .filter(entity -> TeamEnum.FRIEND.equals(entity.team))
                .filter(entity -> UnitTypeEnum.HERO.equals(entity.unitType))
                .filter(entity -> heroEnum.equals(entity.heroType))
                .findFirst();
    }

    public static Entity findTower(List<Entity> entities, TeamEnum teamEnum) {
        return entities.stream()
                .filter(entity -> teamEnum.equals(entity.team))
                .filter(entity -> UnitTypeEnum.TOWER.equals(entity.unitType))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
