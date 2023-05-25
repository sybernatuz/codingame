package strategies;

import enums.SideEnum;
import enums.TeamEnum;
import enums.UnitTypeEnum;
import objects.Coordinate;
import objects.Entity;
import objects.Game;
import utils.ComputeUtils;
import utils.SearchUtils;

import java.util.Comparator;
import java.util.Optional;

public class MoveStrategy {

    public Optional<String> process(Game game) {
        Entity myTower = SearchUtils.findTower(game.entities, TeamEnum.FRIEND);

        double healthPercent = game.currentHero.entity.getCurrentHealthPercent();
        if (healthPercent < 30) {
            return Optional.of(String.format("MOVE %s %s", myTower.coordinate.x, myTower.coordinate.y));
        }

        Optional<Entity> closestUnitEnemyFromTower = findClosestUnitEnemyFromTower(game, myTower);
        if (closestUnitEnemyFromTower.isPresent()) {
            double distance = ComputeUtils.computeDistance(closestUnitEnemyFromTower.get().coordinate, game.currentHero.entity.coordinate);
            if (distance < game.currentHero.entity.attackRange) {
                return closestUnitEnemyFromTower.map(entity -> getCoordinateToMove(game, entity, game.currentHero.entity))
                        .map(coordinate -> String.format("MOVE %s %s", coordinate.x, coordinate.y));
            }
        }
        return Optional.empty();
    }

    private Optional<Entity> findClosestUnitEnemyFromTower(Game game, Entity myTower) {
        return game.entities.stream()
                .filter(entity -> entity.team.equals(TeamEnum.ENEMY))
                .filter(entity -> entity.unitType.equals(UnitTypeEnum.UNIT))
                .min(Comparator.comparing(entity -> ComputeUtils.computeDistance(entity.coordinate, myTower.coordinate)));

    }

    private Coordinate getCoordinateToMove(Game game, Entity entity, Entity myHero) {
        int moveToX = SideEnum.LEFT.equals(game.side) ?
                entity.coordinate.x - myHero.attackRange
                : entity.coordinate.x + myHero.attackRange;
        return new Coordinate(moveToX, entity.coordinate.y);
    }
}
