package strategies;

import inmemory.InMemory;
import objects.Graph;
import objects.Zone;
import objects.ZoneType;
import singleton.Beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LowCrystalStrategy {


    public List<Zone> goToAllFood(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);
        inMemory.distancesBetweenImportantZones.stream()
                .filter(distance -> graph.myBases.contains(distance.source))
                .filter(distance -> distance.target.type.equals(ZoneType.FOOD))
                .filter(distance -> distance.target.resources > 0)
                .sorted(Comparator.comparing(distance -> distance.value))
                .map(distance -> distance.target)
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
        zones.addAll(neighboursOptimization(zones, graph));
        return zones;
    }

    private void searchBestPathFromComputedZone(Zone foodZone, Graph graph, List<Zone> zones) {
        if (zones.contains(foodZone))
            return;
        zones.stream()
                .map(zone -> Beans.searchSpecificZone.search(graph, zone, foodZone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(path -> path.zones.size()))
                .ifPresent(path -> zones.addAll(path.zones));
    }

    private List<Zone> neighboursOptimization(List<Zone> zones, Graph graph) {
        return zones.stream()
                .map(zone -> graph.graph.get(zone))
                .flatMap(List::stream)
                .filter(zone -> !zones.contains(zone))
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .filter(neighbours -> neighbours.resources > 0)
                .collect(Collectors.toList());
    }
}
