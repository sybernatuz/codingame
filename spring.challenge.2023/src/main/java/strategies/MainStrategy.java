package strategies;

import inmemory.InMemory;
import objects.Graph;
import objects.Path;
import objects.Zone;
import singleton.Beans;

import java.util.*;
import java.util.stream.Collectors;

public class MainStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);

        boolean firstClosestEggStep = inMemory.totalCrystals >= 800 && firstClosestEggStep(zones, inMemory, graph);

        long limit = computeNodesLimitForThisTurn(inMemory);

        if (!firstClosestEggStep) {
            searchBestPaths(graph, inMemory, limit, zones);

            if (zones.size() == graph.myBases.size())
                searchBestPaths(graph, inMemory, Long.MAX_VALUE, zones);
        }
        return zones;
    }

    private void searchBestPaths(Graph graph, InMemory inMemory, long limit, List<Zone> zones) {
        inMemory.distancesBetweenImportantZones.stream()
                .filter(distance -> graph.myBases.contains(distance.source))
                .sorted(Comparator.comparing(distance -> distance.value))
                .limit(limit)
                .filter(distance -> distance.target.resources > 0)
                .map(distance -> distance.target)
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
    }

    private boolean firstClosestEggStep(List<Zone> zones, InMemory inMemory, Graph graph) {
        List<Path> firstEggsPath = inMemory.pathToClosestEggFromBase.values().stream()
                        .filter(path -> {
                            int firstClosestEggIndex = path.zones.size() - 1;
                            Zone firstClosestEgg = path.zones.get(firstClosestEggIndex);

                            Zone sourceBase = path.zones.get(0);
                            return firstClosestEgg.resources > 0 && graph.myBases.contains(sourceBase);
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

    private long computeNodesLimitForThisTurn(InMemory inMemory) {
        int totalResourceNodes = inMemory.foodZones.size() + inMemory.eggZones.size();
        long step = Math.round(totalResourceNodes * 0.2);
        long limit = step + inMemory.turn;
        long sixtyPercentOfResourceNodes = Math.round(totalResourceNodes * 0.60);
        if (limit >= sixtyPercentOfResourceNodes)
            limit = sixtyPercentOfResourceNodes;
        return limit;
    }
}
