package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.strategies.build.barracks.ArcherBarrackStrategy;
import main.java.compete.code_royal.strategies.build.barracks.GiantBarrackStrategy;
import main.java.compete.code_royal.strategies.build.barracks.KnightBarrackStrategy;
import main.java.compete.code_royal.utils.DangerUtils;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.MineUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BuildStrategy {

    public Build computeBuild(Unit queen, List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        sites = TowerUtils.removeByOwer(sites, OwnerEnum.ENEMY);
        return /*isFirstAction(sites, gameInfo)*/
                isAvoidDangerAction(sites, gameInfo, units)
                .orElse(isDestroyStrategy(queen, sites)
                .orElse(isMineStrategy(sites, gameInfo, queen)
                .orElse(isKnightBarrackStrategy(sites, gameInfo)
//                .orElse(isArcherBarrackStrategy(sites, gameInfo)
                .orElse(isGiantBarrackStrategy(sites, gameInfo)
                .orElse(isTowerStrategy(sites, gameInfo, queen)
                .orElse(null))))));
    }

    private Optional<Build> isAvoidDangerAction(List<Site> sites, GameInfo gameInfo, List<Unit> units) {
        if (!DangerUtils.isDangerIncoming(units))
            return Optional.empty();
        Site closestExtremityTower = FindUtils.findTheClosestSite(gameInfo.opposedY, TowerUtils.findByOwner(sites, OwnerEnum.FRIEND));
        return Optional.of(new Build(closestExtremityTower, StructureTypeEnum.TOWER));
    }

    private Optional<Build> isFirstAction(List<Site> sites, GameInfo gameInfo) {
        if (FindUtils.findByOwner(sites, OwnerEnum.FRIEND).size() > 0)
            return Optional.empty();

        Site siteToBuild = FindUtils.findTheClosestSite(gameInfo.start, sites);
        return Optional.of(new Build(siteToBuild, BarrackTypeEnum.KNIGHT));
    }

    private Optional<Build> isDestroyStrategy(Unit queen, List<Site> sites) {
        List<Site> enemySites = BarrackUtils.findTrainingBarrack(sites, OwnerEnum.ENEMY);
        Site reachableSite = FindUtils.findReachableSite(enemySites, queen);
        if (reachableSite == null)
            return Optional.empty();
        return Optional.of(new Build(reachableSite, StructureTypeEnum.TOWER));
    }

    private Optional<Build> isMineStrategy(List<Site> sites, GameInfo gameInfo, Unit queen) {
        List<Site> mines = MineUtils.findByOwner(sites, OwnerEnum.FRIEND);
        int totalIncome = mines.stream()
                .mapToInt(mine -> mine.param1)
                .sum();
        boolean isEnoughMine = totalIncome >= 5;
        Optional<Site> mineToUpgrade = MineUtils.findMineToUpgrade(sites);
        if (isEnoughMine && !mineToUpgrade.isPresent())
            return Optional.empty();
        return MineStrategy.computeMine(sites, gameInfo, queen);
    }

    private Optional<Build> isTowerStrategy(List<Site> sites, GameInfo gameInfo, Unit queen) {
        Site closestSite = FindUtils.findTheClosestSiteByOwners(queen.coordinate, sites, Arrays.asList(OwnerEnum.ENEMY, OwnerEnum.NONE));
        Optional<Site> lowRadiusTower = TowerUtils.findByLowRadiusAndClose(sites, OwnerEnum.FRIEND, queen);
        if (!lowRadiusTower.isPresent() && closestSite == null)
            return Optional.empty();
        return TowerStrategy.computeTower(sites, gameInfo, queen);
    }

    private Optional<Build> isArcherBarrackStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> archerBarracks = BarrackUtils.findByBarrackTypeAndOwner(sites, BarrackTypeEnum.ARCHER, OwnerEnum.FRIEND);
        boolean isEnoughArcherBarracks = archerBarracks.size() >= 1;
        if (isEnoughArcherBarracks)
            return Optional.empty();
        return ArcherBarrackStrategy.computeArcherBarrack(sites, gameInfo);
    }

    private Optional<Build> isKnightBarrackStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> knightBarracks = BarrackUtils.findByBarrackTypeAndOwner(sites, BarrackTypeEnum.KNIGHT, OwnerEnum.FRIEND);
        boolean isEnoughKnightBarracks = knightBarracks.size() >= 1;
        if (isEnoughKnightBarracks)
            return Optional.empty();
        return KnightBarrackStrategy.computeKnightBarrack(sites, gameInfo);
    }

    private Optional<Build> isGiantBarrackStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> giantBarracks = BarrackUtils.findByBarrackTypeAndOwner(sites, BarrackTypeEnum.GIANT, OwnerEnum.FRIEND);
        List<Site> enemyTowers = TowerUtils.findByOwner(sites, OwnerEnum.ENEMY);
        boolean isEnoughGiantBarracks = giantBarracks.size() >= 1;
        boolean isEnemyTowersToDestroy = enemyTowers.size() >= 2;
        if (isEnoughGiantBarracks || !isEnemyTowersToDestroy)
            return Optional.empty();
        return GiantBarrackStrategy.computeGiantBarrack(sites, gameInfo);
    }


}
