package strategies.build.barracks;


import enums.BarrackTypeEnum;
import objects.Build;
import objects.GameInfo;
import objects.Site;
import utils.site.FindUtils;

import java.util.List;
import java.util.Optional;

public class KnightBarrackStrategy {

    public static Optional<Build> computeKnightBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestSiteToTheStart = FindUtils.findClosestNotFriendSiteFromCoordinate(sites, gameInfo.mapCenter);
        return Optional.of(new Build(closestSiteToTheStart, BarrackTypeEnum.KNIGHT));
    }
}
