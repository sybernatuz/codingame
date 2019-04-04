package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.BarrackUtils;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.MineUtils;

import java.util.List;
import java.util.Optional;

public class MineStrategy {

    public static Optional<Build> computeMine(List<Site> sites, GameInfo gameInfo) {
        Optional<Site> mineToUpgrade = MineUtils.findMineToUpgrade(sites);
        if (mineToUpgrade.isPresent())
            return Optional.of(new Build(mineToUpgrade.get(), StructureTypeEnum.MINE));

        List<Site> notEmptyGoldSites = MineUtils.findByNotEmptyGoldSite(sites);
        List<Site> trainningSites = BarrackUtils.findTrainingBarrack(sites, OwnerEnum.FRIEND);
        trainningSites.forEach(notEmptyGoldSites::remove);
        Site siteToBuild = FindUtils.findTheClosestSite(gameInfo.start, notEmptyGoldSites);
        return Optional.of(new Build(siteToBuild, StructureTypeEnum.MINE));
    }
}
