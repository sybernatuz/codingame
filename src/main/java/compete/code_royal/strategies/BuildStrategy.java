package main.java.compete.code_royal.strategies;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.FindUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuildStrategy {

    public Build computeBuild(Unit queen, List<Site> sites, GameInfo gameInfo) {
        return isDestroyStrategy(queen, sites)
                .orElse(isMineToUpgrade(sites)
                        .map(mine -> new Build(mine, StructureTypeEnum.MINE))
                        .orElse(startBuild(sites, gameInfo)));
    }


    private Build startBuild(List<Site> sites, GameInfo gameInfo) {
        List<Site> abandonedSites = FindUtils.findByOwner(sites, OwnerEnum.NONE);
        List<Site> enemySites = FindUtils.findByOwner(sites, OwnerEnum.ENEMY);
        List<Site> possibleSites = Stream.concat(abandonedSites.stream(), enemySites.stream())
                .collect(Collectors.toList());
        Site closestSiteToTheCenter = FindUtils.findTheClosestSite(gameInfo.mapCenter, possibleSites);
        Site closestSiteToTheStart = FindUtils.findTheClosestSite(gameInfo.start, possibleSites);
        List<Site> archerBarracks = FindUtils.findByTypeAndOwner(sites, BarrackTypeEnum.ARCHER, OwnerEnum.FRIEND);
        List<Site> knightBarracks = FindUtils.findByTypeAndOwner(sites, BarrackTypeEnum.KNIGHT, OwnerEnum.FRIEND);
        List<Site> towers = FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.TOWER ,OwnerEnum.FRIEND);
        List<Site> mines = FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.MINE ,OwnerEnum.FRIEND);
        if (mines.size() < 1)
            return new Build(closestSiteToTheStart, StructureTypeEnum.MINE);
        if (archerBarracks.size() < 1)
            return new Build(closestSiteToTheStart, BarrackTypeEnum.ARCHER);
        if (knightBarracks.size() < 2)
            return new Build(closestSiteToTheCenter, BarrackTypeEnum.KNIGHT);
        if (towers.size() < 2)
            return new Build(closestSiteToTheStart, StructureTypeEnum.TOWER);
        return null;
    }

    private Optional<Build> isDestroyStrategy(Unit queen, List<Site> sites) {
        List<Site> friendSites = FindUtils.findByOwner(sites, OwnerEnum.FRIEND);
        List<Site> enemySites = FindUtils.findTrainingBarrack(sites, OwnerEnum.ENEMY);
        Site reachableSite = FindUtils.findReachableSite(enemySites, queen);
        if (friendSites.size() < 2 || reachableSite == null)
            return Optional.empty();
        return Optional.of(new Build(reachableSite, BarrackTypeEnum.KNIGHT));
    }

    private Optional<Site> isMineToUpgrade(List<Site> sites) {
        List<Site> mines = FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.MINE ,OwnerEnum.FRIEND);
        return mines.stream()
                .filter(mine -> mine.param1 < 3)
                .findFirst();
    }


}
