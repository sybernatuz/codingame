package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.strategies.build.barracks.ArcherBarrackStrategy;
import main.java.compete.code_royal.strategies.build.barracks.KnightBarrackStrategy;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.MineUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.List;
import java.util.Optional;

public class BuildStrategy {

    public Build computeBuild(Unit queen, List<Site> sites, GameInfo gameInfo) {
        return isDestroyStrategy(queen, sites)
                .orElse(isMineStrategy(sites, gameInfo)
                .orElse(isTowerStrategy(sites, gameInfo)
                .orElse(isArcherBarrackStrategy(sites, gameInfo)
                .orElse(isKnightBarrackStrategy(sites, gameInfo)
                .orElse(null)))));
    }

    private Optional<Build> isDestroyStrategy(Unit queen, List<Site> sites) {
        List<Site> friendSites = FindUtils.findByOwner(sites, OwnerEnum.FRIEND);
        List<Site> enemySites = BarrackUtils.findTrainingBarrack(sites, OwnerEnum.ENEMY);
        Site reachableSite = FindUtils.findReachableSite(enemySites, queen);
        if (friendSites.size() < 2 || reachableSite == null)
            return Optional.empty();
        return Optional.of(new Build(reachableSite, StructureTypeEnum.TOWER));
    }

    private Optional<Build> isMineStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> mines = MineUtils.findByOwner(sites, OwnerEnum.FRIEND);
        boolean isEnoughMine = mines.size() >= 3;
        Optional<Site> mineToUpgrade = MineUtils.findMineToUpgrade(sites);
        if (!isEnoughMine || mineToUpgrade.isPresent())
            return MineStrategy.computeMine(sites, gameInfo);
        return Optional.empty();
    }

    private Optional<Build> isTowerStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> towers = TowerUtils.findByOwner(sites, OwnerEnum.FRIEND);
        boolean isEnoughTower = towers.size() >= 2;
        Optional<Site> lowRadiusTower = TowerUtils.findByLowRadius(sites, OwnerEnum.FRIEND);
        if (!isEnoughTower || lowRadiusTower.isPresent())
            return TowerStrategy.computeTower(sites, gameInfo);
        return Optional.empty();
    }

    private Optional<Build> isArcherBarrackStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> archerBarracks = BarrackUtils.findByBarrackTypeAndOwner(sites, BarrackTypeEnum.ARCHER, OwnerEnum.FRIEND);
        boolean isEnoughArcherBarracks = archerBarracks.size() >= 1;
        if (!isEnoughArcherBarracks)
            return ArcherBarrackStrategy.computeArcherBarrack(sites, gameInfo);
        return Optional.empty();
    }

    private Optional<Build> isKnightBarrackStrategy(List<Site> sites, GameInfo gameInfo) {
        List<Site> knightBarracks = BarrackUtils.findByBarrackTypeAndOwner(sites, BarrackTypeEnum.KNIGHT, OwnerEnum.FRIEND);
        boolean isEnoughArcherBarracks = knightBarracks.size() >= 2;
        if (!isEnoughArcherBarracks)
            return KnightBarrackStrategy.computeKnightBarrack(sites, gameInfo);
        return Optional.empty();
    }


}
