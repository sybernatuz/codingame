package main.java.compete.code_royal.strategies.build.barracks;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.FindUtils;

import java.util.List;
import java.util.Optional;

public class KnightBarrackStrategy {

    public static Optional<Build> computeKnightBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestSiteToTheStart = FindUtils.findClosestNotFriendSiteFromStart(sites, gameInfo);
        return Optional.of(new Build(closestSiteToTheStart, BarrackTypeEnum.KNIGHT));
    }
}
