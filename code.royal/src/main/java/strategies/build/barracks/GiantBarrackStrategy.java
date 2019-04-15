package strategies.build.barracks;


import enums.BarrackTypeEnum;
import enums.OwnerEnum;
import objects.Build;
import objects.GameInfo;
import objects.Site;
import utils.site.FindUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GiantBarrackStrategy {

    public static Optional<Build> computeGiantBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestBuildable = FindUtils.findTheClosestBuildable(gameInfo.mapCenter, sites, Arrays.asList(OwnerEnum.NONE, OwnerEnum.ENEMY));
        return Optional.of(new Build(closestBuildable, BarrackTypeEnum.GIANT));
    }
}
