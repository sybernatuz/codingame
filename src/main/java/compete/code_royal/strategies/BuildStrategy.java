package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.FindUtils;

import java.util.List;

public class BuildStrategy {

    public Build computeBuild(Unit queen, List<Site> sites, GameInfo gameInfo) {
        if (isDestroyStrategy(sites, queen))
            return destroyEnemyBuild(queen, sites);
        if (isBuildStrategy(sites))
            return startBuild(sites, gameInfo);
        return null;
    }

    private Build startBuild(List<Site> sites, GameInfo gameInfo) {
        List<Site> abandonedSites = FindUtils.findByOwner(sites, OwnerEnum.NONE);
        Site closestSiteToTheCenter = FindUtils.findTheClosestSite(gameInfo.mapCenter, abandonedSites);
        Site closestSiteToTheStart = FindUtils.findTheClosestSite(gameInfo.start, abandonedSites);
        List<Site> archerBarracks = FindUtils.findByTypeAndOwner(sites, BarrackTypeEnum.ARCHER, OwnerEnum.FRIEND);
        if (!archerBarracks.isEmpty())
            return new Build(closestSiteToTheCenter, BarrackTypeEnum.KNIGHT);
        else
            return new Build(closestSiteToTheStart, BarrackTypeEnum.ARCHER);
    }

    private Build destroyEnemyBuild(Unit queen, List<Site> sites) {
        List<Site> enemySites = FindUtils.findByOwner(sites, OwnerEnum.ENEMY);
        Site reachableSite = FindUtils.findReachableSite(enemySites, queen);
        return new Build(reachableSite, BarrackTypeEnum.KNIGHT);
    }

    private boolean isDestroyStrategy(List<Site> sites, Unit queen) {
        List<Site> friendSites = FindUtils.findByOwner(sites, OwnerEnum.FRIEND);
        List<Site> enemyTrainingSites = FindUtils.findTrainingBarrack(sites, OwnerEnum.ENEMY);
        Site reachableSite = FindUtils.findReachableSite(enemyTrainingSites, queen);
        return friendSites.size() >= 2 && reachableSite != null;
    }

    private boolean isBuildStrategy(List<Site> sites) {
        List<Site> archerBarracks = FindUtils.findByTypeAndOwner(sites, BarrackTypeEnum.ARCHER, OwnerEnum.FRIEND);
        List<Site> knightBarracks = FindUtils.findByTypeAndOwner(sites, BarrackTypeEnum.KNIGHT, OwnerEnum.FRIEND);
        boolean isEnoughArchery = archerBarracks.size() > 1;
        boolean isEnoughKnightBarrack = knightBarracks.size() >= 2;
        return !isEnoughArchery && !isEnoughKnightBarrack;
    }
}
