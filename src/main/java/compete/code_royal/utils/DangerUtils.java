package main.java.compete.code_royal.utils;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.UnitUtils;

import java.util.List;
import java.util.Optional;

public class DangerUtils {

    public static boolean isDangerIncoming(List<Unit> units) {
        Unit queen = UnitUtils.findQueen(units, OwnerEnum.FRIEND);
        Optional<Unit> closestEnemyKnightGroup = UnitUtils.findTheClosestEnemyKnightGroup(queen, units);
        int dangerRange = 1000 - queen.health*10;
        return closestEnemyKnightGroup
                .filter(unit -> ComputeUtils.computeDistance(unit.coordinate, queen.coordinate) < dangerRange)
                .isPresent();

    }
}
