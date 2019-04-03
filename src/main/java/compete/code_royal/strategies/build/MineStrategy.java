package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.MineUtils;

import java.util.List;
import java.util.Optional;

public class MineStrategy {

    public static Optional<Build> computeMine(List<Site> sites, GameInfo gameInfo) {
        Optional<Site> lowRadiusMine = MineUtils.findMineToUpgrade(sites);
        if (lowRadiusMine.isPresent())
            return Optional.of(new Build(lowRadiusMine.get(), StructureTypeEnum.MINE));

        Coordinate startExtremity = FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);
        Coordinate closestExtremity = gameInfo.extremities.stream()
                .filter(coordinate -> coordinate.x == startExtremity.x && coordinate.y != startExtremity.y)
                .findFirst()
                .orElse(null);
        List<Site> notEmptyGoldSites = MineUtils.findByNotEmptyGoldSite(sites);
        Site siteToBuild = FindUtils.findTheClosestSite(closestExtremity, notEmptyGoldSites);
        return Optional.of(new Build(siteToBuild, StructureTypeEnum.MINE));
    }
}
