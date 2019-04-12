package utils;

import objects.Coordinate;
import objects.Site;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortUtils {

    public static List<Site> sortByDistanceToCordinate(List<Site> sites, Coordinate coordinate) {
        return sites.stream()
                .sorted(Comparator.comparing(site -> ComputeUtils.computeDistance(coordinate, site.coordinate)))
                .collect(Collectors.toList());
    }
}
