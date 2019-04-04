package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TowerStrategy {

    public static Optional<Build> computeTower(List<Site> sites, GameInfo gameInfo, Unit queen) {
        Optional<Site> lowRadiusTower = TowerUtils.findByLowRadiusAndClose(sites, OwnerEnum.FRIEND, queen);
        if (lowRadiusTower.isPresent())
            return Optional.of(new Build(lowRadiusTower.get(), StructureTypeEnum.TOWER));


        Site closestSite = FindUtils.findTheClosestBuildable(gameInfo.opposedY, sites, Arrays.asList(OwnerEnum.ENEMY, OwnerEnum.NONE));
        return Optional.of(new Build(closestSite, StructureTypeEnum.TOWER));
    }
}
