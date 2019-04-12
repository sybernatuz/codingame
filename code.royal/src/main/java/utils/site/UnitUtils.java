package utils.site;


import enums.OwnerEnum;
import enums.UnitTypeEnum;
import objects.Unit;
import utils.ComputeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitUtils {

    public static Unit findQueen(List<Unit> units, OwnerEnum owner) {
        return units.stream()
                .filter(unit -> unit.unitType.equals(UnitTypeEnum.QUEEN))
                .filter(unit -> unit.owner.equals(owner))
                .findFirst()
                .orElse(null);
    }

    public static List<Unit> findByUnitType(List<Unit> units, OwnerEnum owner, UnitTypeEnum unitType) {
        return units.stream()
                .filter(unit -> unit.owner.equals(owner))
                .filter(unit -> unit.unitType.equals(unitType))
                .collect(Collectors.toList());
    }

    public static Optional<Unit> findTheClosestEnemyKnightGroup(Unit queen, List<Unit> units) {
        List<Unit> enemyKnights = findByUnitType(units, OwnerEnum.ENEMY, UnitTypeEnum.KNIGHT);
        return enemyKnights.stream()
                .min(Comparator.comparing(unit -> ComputeUtils.computeDistance(unit.coordinate, queen.coordinate)));
    }
}
