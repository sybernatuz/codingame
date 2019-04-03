package main.java.compete.code_royal.strategies.build;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Build;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.site.FindUtils;
import main.java.compete.code_royal.utils.site.TowerUtils;

import java.util.List;
import java.util.Optional;

public class TowerStrategy {

    public static Optional<Build> computeTower(List<Site> sites, GameInfo gameInfo) {
        Optional<Site> lowRadiusTower = TowerUtils.findByLowRadius(sites, OwnerEnum.FRIEND);
        if (lowRadiusTower.isPresent())
            return Optional.of(new Build(lowRadiusTower.get(), StructureTypeEnum.TOWER));

        Site closestSiteToTheStart = FindUtils.findClosestNotFriendSiteFromStart(sites, gameInfo);
        return Optional.of(new Build(closestSiteToTheStart, StructureTypeEnum.TOWER));
    }
}
