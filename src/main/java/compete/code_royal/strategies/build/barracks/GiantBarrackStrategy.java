package main.java.compete.code_royal.strategies.build.barracks;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.FindUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GiantBarrackStrategy {

    public static Optional<Build> computeGiantBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestBuildable = FindUtils.findTheClosestBuildable(gameInfo.mapCenter, sites, Arrays.asList(OwnerEnum.NONE, OwnerEnum.ENEMY));
        return Optional.of(new Build(closestBuildable, BarrackTypeEnum.GIANT));
    }
}
