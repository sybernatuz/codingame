package utils.site;


import enums.OwnerEnum;
import enums.StructureTypeEnum;
import objects.Site;
import objects.Unit;
import utils.ComputeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TowerUtils {

    public static List<Site> findByOwner(List<Site> sites, OwnerEnum owner) {
        return FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.TOWER, owner);
    }

    public static List<Site> removeByOwer(List<Site> sites, OwnerEnum owner) {
        List<Site> towers = FindUtils.findByStructureTypeAndOwner(sites, StructureTypeEnum.TOWER, owner);
        return sites.stream()
                .filter(site -> !towers.contains(site))
                .collect(Collectors.toList());
    }

    public static Optional<Site> findByLowRadiusAndClose(List<Site> sites, OwnerEnum owner, Unit queen) {
        return findByOwner(sites, owner).stream()
                .filter(tower -> tower.param2 < 300)
                .filter(tower -> ComputeUtils.computeDistance(tower.coordinate, queen.coordinate) < 200)
                .findFirst();
    }
}
