package main.java.compete.code_royal.utils.site;

import main.java.compete.code_royal.enums.OwnerEnum;
import main.java.compete.code_royal.enums.StructureTypeEnum;
import main.java.compete.code_royal.objects.Coordinate;
import main.java.compete.code_royal.objects.GameInfo;
import main.java.compete.code_royal.objects.Site;
import main.java.compete.code_royal.objects.Unit;
import main.java.compete.code_royal.utils.ComputeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static Site findTheClosestBuildable(Coordinate coordinate, List<Site> sites, List<OwnerEnum> owners) {
        List<Site> buildableSites = FindUtils.findBuildable(sites);
        return findTheClosestSite(coordinate, findByOwners(buildableSites, owners));
    }

    public static List<Site> findBuildable(List<Site> sites) {
        List<Site> enemyTowers = TowerUtils.findByOwner(sites, OwnerEnum.ENEMY);
        return sites.stream()
                .filter(site -> !enemyTowers.contains(site))
                .collect(Collectors.toList());
    }

    public static Site findTheClosestSiteByOwners(Coordinate coordinate, List<Site> sites, List<OwnerEnum> owners) {
        return findTheClosestSite(coordinate, findByOwners(sites, owners));
    }

    public static Coordinate findTheClosestCoordinate(Coordinate coordinate, List<Coordinate> extremities) {
        return extremities.stream()
                .min(Comparator.comparing(extremity -> ComputeUtils.computeDistance(coordinate, extremity)))
                .orElse(null);
    }

    public static Site findClosestSiteToOpposedExtremity(List<Site> sites, GameInfo gameInfo) {
        Coordinate startExtremity = FindUtils.findTheClosestCoordinate(gameInfo.start, gameInfo.extremities);
        Coordinate closestExtremity = gameInfo.extremities.stream()
                .filter(coordinate -> coordinate.x == startExtremity.x && coordinate.y != startExtremity.y)
                .findFirst()
                .orElse(null);
        return FindUtils.findTheClosestSite(closestExtremity, sites);
    }

    public static List<Site> findByOwner(List<Site> sites, OwnerEnum owner) {
        return sites.stream()
                .filter(site -> site.owner.equals(owner))
                .collect(Collectors.toList());
    }

    public static List<Site> findByOwners(List<Site> sites, List<OwnerEnum> owners) {
        return sites.stream()
                .filter(site -> owners.contains(site.owner))
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

    public static Site findClosestNotFriendSiteFromCoordinate(List<Site> sites, Coordinate coordinate) {
        List<Site> abandonedSites = FindUtils.findByOwner(sites, OwnerEnum.NONE);
        List<Site> enemySites = FindUtils.findByOwner(sites, OwnerEnum.ENEMY);
        List<Site> possibleSites = Stream.concat(abandonedSites.stream(), enemySites.stream())
                .collect(Collectors.toList());
        return FindUtils.findTheClosestSite(coordinate, possibleSites);
    }

}
