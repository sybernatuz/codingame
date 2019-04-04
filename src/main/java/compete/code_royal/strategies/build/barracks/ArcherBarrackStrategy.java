package main.java.compete.code_royal.strategies.build.barracks;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.FindUtils;

import java.util.List;
import java.util.Optional;

public class ArcherBarrackStrategy {

    public static Optional<Build> computeArcherBarrack(List<Site> sites, GameInfo gameInfo) {
        Site closestSiteToTheStart = FindUtils.findClosestNotFriendSiteFromCoordinate(sites, gameInfo.start);
        return Optional.of(new Build(closestSiteToTheStart, BarrackTypeEnum.ARCHER));
    }
}
