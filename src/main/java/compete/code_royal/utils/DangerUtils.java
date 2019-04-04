package main.java.compete.code_royal.utils;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.UnitUtils;

import java.util.List;
import java.util.Optional;

public class DangerUtils {

    public static boolean isDangerIncomming(List<Unit> units) {
        Unit queen = UnitUtils.findQueen(units, OwnerEnum.FRIEND);
        Optional<Unit> closestEnemyKnightGroup = UnitUtils.findTheClosestEnemyKnightGroup(queen, units);
        return closestEnemyKnightGroup
                .filter(unit -> ComputeUtils.computeDistance(unit.coordinate, queen.coordinate) < 400)
                .isPresent();

    }
}
