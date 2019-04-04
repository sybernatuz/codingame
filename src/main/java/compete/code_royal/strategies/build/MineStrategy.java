package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.MineUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.List;
import java.util.Optional;

public class MineStrategy {

    public static Optional<Build> computeMine(List<Site> sites, GameInfo gameInfo, Unit queen) {
        Optional<Site> mineToUpgrade = MineUtils.findMineToUpgrade(sites);
        if (mineToUpgrade.isPresent())
            return Optional.of(new Build(mineToUpgrade.get(), StructureTypeEnum.MINE));

        List<Site> notEmptyGoldSites = MineUtils.findByNotEmptyGoldSite(sites);
        if (notEmptyGoldSites.isEmpty()) {
            Optional<Site> unknownIncomeRateMine = MineUtils.findClosestUnknownIncomeRate(sites, queen.coordinate);
            return unknownIncomeRateMine.map(site -> new Build(site, StructureTypeEnum.MINE));
        }
        List<Site> trainingSites = BarrackUtils.findTrainingBarrack(sites, OwnerEnum.FRIEND);
        List<Site> friendTowers = TowerUtils.findByOwner(sites, OwnerEnum.FRIEND);
        trainingSites.forEach(notEmptyGoldSites::remove);
        friendTowers.forEach(notEmptyGoldSites::remove);
        Site siteToBuild = FindUtils.findTheClosestSite(gameInfo.start, notEmptyGoldSites);
        if (siteToBuild == null)
            return Optional.empty();
        return Optional.of(new Build(siteToBuild, StructureTypeEnum.MINE));
    }
}
