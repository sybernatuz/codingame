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

    public static Site findTheClosestSiteByOwner(Coordinate coordinate, List<Site> sites, OwnerEnum owner) {
        return findTheClosestSite(coordinate, findByOwner(sites, owner));
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

    public static Site findClosestNotFriendSiteFromStart(List<Site> sites, GameInfo gameInfo) {
        List<Site> abandonedSites = FindUtils.findByOwner(sites, OwnerEnum.NONE);
        List<Site> enemySites = FindUtils.findByOwner(sites, OwnerEnum.ENEMY);
        List<Site> possibleSites = Stream.concat(abandonedSites.stream(), enemySites.stream())
                .collect(Collectors.toList());
        return FindUtils.findTheClosestSite(gameInfo.start, possibleSites);
    }

}
