package strategies;

import inmemory.InMemory;
import objects.*;
import singleton.Beans;
import utils.ComparatorUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);

        boolean firstClosestEggStep = inMemory.totalCrystals >= 100
                && inMemory.turn <= 3
                && firstClosestEggStep(zones, inMemory, graph);


        if (!firstClosestEggStep) {
            long limit = computeNodesLimit(inMemory);
            searchBestPaths(graph, inMemory, limit, zones);

            if (zones.size() == graph.myBases.size())
                searchBestPaths(graph, inMemory, zones);
        }

        zones.addAll(neighboursOptimization(zones, graph, inMemory));
        return zones;
    }

    private void searchBestPaths(Graph graph, InMemory inMemory, long limit, List<Zone> zones) {
        inMemory.zoneToGo.stream()
                .filter(zone -> zone.resources > 0)
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory))
                .limit(limit)
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
    }

    private void searchBestPaths(Graph graph, InMemory inMemory, List<Zone> zones) {
        inMemory.foodZones.stream()
                .filter(zone -> zone.resources > 0)
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory))
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
    }

    private boolean firstClosestEggStep(List<Zone> zones, InMemory inMemory, Graph graph) {
        List<Path> firstEggsPath = graph.myBases.stream()
                        .map(base -> Beans.searchClosestEgg.search(graph, base))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(path -> {
                            int firstClosestEggIndex = path.zones.size() - 1;
                            Zone firstClosestEgg = path.zones.get(firstClosestEggIndex);

                            Zone sourceBase = path.zones.get(0);

                            return firstClosestEgg.resources > 0
                                    && graph.myBases.contains(sourceBase)
                                    && inMemory.zoneToGo.contains(firstClosestEgg);
                        })
                        .peek(path -> zones.addAll(path.zones))
                        .collect(Collectors.toList());

        return firstEggsPath.size() == 2 || (firstEggsPath.size() == 1 && graph.myBases.size() == 1);
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

    private long computeNodesLimit(InMemory inMemory) {
        long step = Math.round(inMemory.zoneToGo.size() * 0.4);
        long limit = step * inMemory.step;
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
