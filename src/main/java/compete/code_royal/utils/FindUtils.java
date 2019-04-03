package main.java.compete.code_royal.utils;

import main.java.compete.code_royal.enums.BarrackTypeEnum;
import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.enums.UnitTypeEnum;
import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FindUtils {

    public static Site findSiteById(List<Site> sites, int id) {
        return sites.stream()
                .filter(site -> site.siteId == id)
                .findFirst()
                .orElse(null);
    }

    public static Site findTheClosestSite(Coordinate coordinate, List<Site> sites) {
        return sites.stream()
                .min(Comparator.comparing(site -> ComputeUtils.computeDistance(coordinate, site.coordinate)))
                .orElse(null);
    }

    public static Site findTheClosestSiteByOwner(Coordinate coordinate, List<Site> sites, OwnerEnum owner) {
        return findTheClosestSite(coordinate, findByOwner(sites, owner));
    }

    public static Coordinate findTheClosestCoordinate(Coordinate coordinate, List<Coordinate> extremities) {
        return extremities.stream()
                .min(Comparator.comparing(extremity -> ComputeUtils.computeDistance(coordinate, extremity)))
                .orElse(null);
    }

    public static List<Site> findByBarrackType(List<Site> sites, BarrackTypeEnum barrackType) {
        return findByStructureType(sites, StructureTypeEnum.BARRACK).stream()
                .filter(site -> BarrackTypeEnum.get(site.param2).equals(barrackType))
                .collect(Collectors.toList());
    }

    public static List<Site> findByOwner(List<Site> sites, OwnerEnum owner) {
        return sites.stream()
                .filter(site -> site.owner.equals(owner))
                .collect(Collectors.toList());
    }

    public static List<Site> findByTypeAndOwner(List<Site> sites, BarrackTypeEnum barrackType, OwnerEnum owner) {
        return findByBarrackType(findByOwner(sites, owner), barrackType);
    }

    public static Unit findQueen(List<Unit> units, OwnerEnum owner) {
        return units.stream()
                .filter(unit -> unit.unitType.equals(UnitTypeEnum.QUEEN))
                .filter(unit -> unit.owner.equals(owner))
                .findFirst()
                .orElse(null);
    }

    public static List<Unit> findByUnitType(List<Unit> units, OwnerEnum owner, UnitTypeEnum unitType) {
        return units.stream()
                .filter(unit -> unit.owner.equals(owner))
                .filter(unit -> unit.unitType.equals(unitType))
                .collect(Collectors.toList());
    }

    public static List<Site> findTrainingBarrack(List<Site> sites, OwnerEnum owner) {
        return findByOwner(findByStructureType(sites, StructureTypeEnum.BARRACK), owner).stream()
                .filter(site -> site.param2 > 0)
                .collect(Collectors.toList());
    }

    public static List<Site> findWaitingBarrack(List<Site> sites, OwnerEnum owner) {
        return findByOwner(findByStructureType(sites, StructureTypeEnum.BARRACK), owner).stream()
                .filter(site -> site.param1 == 0)
                .collect(Collectors.toList());
    }

    public static List<Site> findReachableSites(List<Site> sites, Unit queen) {
        return sites.stream()
                .filter(site -> ComputeUtils.computeTurnToReachCoordinate(site.coordinate, queen.coordinate, 60) <= 4)
                .collect(Collectors.toList());
    }

    public static Site findReachableSite(List<Site> sites, Unit queen) {
        return findReachableSites(sites, queen).stream()
                .findFirst()
                .orElse(null);
    }

    public static List<Site> findByStructureType(List<Site> sites, StructureTypeEnum structureType) {
        return sites.stream()
                .filter(site -> site.structureType.equals(structureType))
                .collect(Collectors.toList());
    }

    public static List<Site> findByStructureTypeAndOwner(List<Site> sites, StructureTypeEnum structureType, OwnerEnum owner) {
        return findByStructureType(findByOwner(sites, owner), structureType);
    }
}
