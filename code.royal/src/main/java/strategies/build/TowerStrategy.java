package strategies.build;


import enums.OwnerEnum;
import enums.StructureTypeEnum;
import objects.Build;
import objects.GameInfo;
import objects.Site;
import objects.Unit;
import utils.ComputeUtils;
import utils.site.FindUtils;
import utils.site.TowerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TowerStrategy {

    public static Optional<Build> computeTower(List<Site> sites, GameInfo gameInfo, Unit queen) {
        Optional<Site> lowRadiusTower = TowerUtils.findByLowRadiusAndClose(sites, OwnerEnum.FRIEND, queen);
        if (lowRadiusTower.isPresent())
            return Optional.of(new Build(lowRadiusTower.get(), StructureTypeEnum.TOWER));

        Optional<Site> closestOptimalSite = getTheClosestToQueenWhenGoToCoordinate(sites, gameInfo, queen);
        if (closestOptimalSite.isPresent())
            return Optional.of(new Build(closestOptimalSite.get(), StructureTypeEnum.TOWER));

        Site closestSite = FindUtils.findTheClosestBuildable(gameInfo.opposedY, sites, Arrays.asList(OwnerEnum.ENEMY, OwnerEnum.NONE));
        return Optional.of(new Build(closestSite, StructureTypeEnum.TOWER));
    }

    private static Optional<Site> getTheClosestToQueenWhenGoToCoordinate(List<Site> sites, GameInfo gameInfo, Unit queen) {
        List<Site> buildableSites = FindUtils.findBuildable(sites);
        buildableSites = FindUtils.findByOwners(buildableSites, Arrays.asList(OwnerEnum.ENEMY, OwnerEnum.NONE));
        Double distanceQueenExtremity = ComputeUtils.computeDistance(queen.coordinate, gameInfo.opposedY);
        while (!buildableSites.isEmpty()) {
            Site closestSite = FindUtils.findTheClosestSite(queen.coordinate, buildableSites);
            Double distanceSiteExtremity = ComputeUtils.computeDistance(closestSite.coordinate, gameInfo.opposedY);
            if (distanceQueenExtremity > distanceSiteExtremity)
                return Optional.of(closestSite);
            buildableSites.remove(closestSite);
        }
        return Optional.empty();
    }
}
