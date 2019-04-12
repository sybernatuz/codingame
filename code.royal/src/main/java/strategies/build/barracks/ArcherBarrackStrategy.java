package strategies.build.barracks;


import enums.BarrackTypeEnum;
import objects.Build;
import objects.GameInfo;
import objects.Site;
import utils.site.FindUtils;

import java.util.List;
import java.util.Optional;

public class ArcherBarrackStrategy {

    public static Optional<Build> computeArcherBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestSiteToTheStart = FindUtils.findClosestNotFriendSiteFromCoordinate(sites, gameInfo.start);
        return Optional.of(new Build(closestSiteToTheStart, BarrackTypeEnum.ARCHER));
    }
}
