package strategies;

import enums.TeamEnum;
import objects.Game;
import utils.ComputeUtils;

import java.util.Comparator;

public class JungleStrategy {

    public String process(Game game) {
        return game.entities.stream()
                .filter(entity -> TeamEnum.NEUTRAL.equals(entity.team))
                .min(Comparator.comparing(entity -> ComputeUtils.computeDistance(game.currentHero.entity.coordinate, entity.coordinate)))
                .map(entity -> String.format("ATTACK %s", entity.unitId))
                .orElse("WAIT");
    }
}
