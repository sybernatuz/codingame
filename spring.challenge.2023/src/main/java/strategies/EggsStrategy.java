package strategies;

import inmemory.InMemory;
import objects.Graph;
import objects.Side;
import objects.Zone;
import objects.ZoneType;
import singleton.Beans;
import utils.ComparatorUtils;
import utils.LogsUtils;
import utils.SearchUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EggsStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);

        int eggsRemaining = inMemory.zoneToGo.stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .mapToInt(zone -> zone.resources)
                .sum();

        int halfOfMyEggs = inMemory.totalEggs / 4;
        if (eggsRemaining >= halfOfMyEggs) {
            focusEggs(graph, inMemory, zones);
            if (inMemory.turn > 3) {
                focusContestedFood(graph, inMemory, zones);
            }
        } else if (eggsRemaining > halfOfMyEggs * 0.25) {
            focusEggs(graph, inMemory, zones);
            focusPrioritizedFood(graph, inMemory, zones);
        } else {
            focusEggs(graph, inMemory, zones);
            focusFood(graph, inMemory, zones);
        }


        zones.addAll(neighboursOptimization(zones, graph, inMemory));
        return zones;
    }

    private void focusContestedFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        SearchUtils.findFoods(inMemory.zoneToGo)
                .filter(zone -> Side.CONTESTED.equals(inMemory.locations.get(zone).side))
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void focusPrioritizedFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        long limit = inMemory.zoneToGo.stream()
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .count() / 2;
        SearchUtils.findFoods(inMemory.zoneToGo)
                .sorted(Comparator.comparing(zone -> inMemory.locations.get(zone).distanceDifferenceWithOppositeBase))
                .limit(limit)
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void focusFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        SearchUtils.findFoods(inMemory.zoneToGo)
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory).reversed())
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void focusEggs(Graph graph, InMemory inMemory, List<Zone> zones) {
        SearchUtils.findEggs(inMemory.zoneToGo)
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory))
                .limit(computeEggsLimit(inMemory, graph))
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void searchBestPathFromComputedZone(Zone foodZone, Graph graph, List<Zone> zones) {
        if (zones.contains(foodZone))
            return;
        zones.stream()
                .map(zone -> Beans.searchSpecificZone.search(graph, zone, foodZone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(path -> path.zones.size()))
                .filter(path -> path.zones.stream().noneMatch(zone -> graph.enemyBases.contains(zone)))
                .ifPresent(path -> zones.addAll(path.zones));
    }

    private long computeEggsLimit(InMemory inMemory, Graph graph) {
        if (inMemory.turn < 3) {
            return graph.myBases.size();
        }
        long limit = graph.myBases.size() + inMemory.step;
        inMemory.step++;
        return limit;
    }

    private List<Zone> neighboursOptimization(List<Zone> zones, Graph graph, InMemory inMemory) {
        return zones.stream()
                .map(zone -> graph.graph.get(zone))
                .flatMap(List::stream)
                .filter(zone -> !zones.contains(zone))
                .filter(zone -> inMemory.zoneToGo.contains(zone))
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .filter(neighbours -> neighbours.resources > 0)
                .collect(Collectors.toList());
    }
}
