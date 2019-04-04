package main.java.compete.code_royal.utils.site;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.utils.ComputeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MineUtils {

    public static List<Site> findByOwner(List<Site> sites, OwnerEnum owner) {
        return FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.MINE, owner);
    }

    public static List<Site> findByNotEmptyGoldSite(List<Site> sites) {
        return sites.stream()
                .filter(site -> site.gold > 1)
                .filter(site -> !site.structureType.equals(StructureTypeEnum.MINE))
                .collect(Collectors.toList());
    }

    public static Optional<Site> findMineToUpgrade(List<Site> sites) {
        List<Site> mines = FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.MINE ,OwnerEnum.FRIEND);
        return mines.stream()
                .filter(mine -> mine.param1 < mine.maxMineSize)
                .findFirst();
    }

    public static Optional<Site> findClosestUnknownIncomeRate(List<Site> sites, Coordinate coordinate) {
        return sites.stream()
                .filter(site -> site.param1 == -1)
                .min(Comparator.comparing(site -> ComputeUtils.computeDistance(site.coordinate, coordinate)));
    }
}
