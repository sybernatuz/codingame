package strategies;

import inmemory.InMemory;
import objects.Graph;
import objects.Zone;
import objects.ZoneType;
import singleton.Beans;
import utils.ComparatorUtils;
import utils.LogsUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//-2988031270051414500
public class HighCrystalStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);
        goToEggs(graph, inMemory, zones);

        if (noMoreEggs(inMemory)) {
            goToFood(graph, inMemory, zones);
        }
        return zones;
    }

    private void goToEggs(Graph graph, InMemory inMemory, List<Zone> zones) {
        inMemory.zoneToGo.stream()
                .filter(zone -> zone.resources > 0)
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory))
                .limit(computeEggsLimit(inMemory, graph))
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void searchBestPathFromComputedZone(Zone zone, Graph graph, List<Zone> zones) {
        if (zones.contains(zone))
            return;
        zones.stream()
                .map(zoneS -> Beans.searchSpecificZone.search(graph, zone, zoneS))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(path -> path.zones.size()))
                .ifPresent(path -> zones.addAll(path.zones));
    }

    private boolean noMoreEggs(InMemory inMemory) {
        return inMemory.zoneToGo.stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .noneMatch(zone -> zone.resources > 0);
    }

    private long computeEggsLimit(InMemory inMemory, Graph graph) {
        if (inMemory.turn < 3) {
            return graph.myBases.size();
        }
        long limit = graph.myBases.size() + inMemory.step;
        inMemory.step++;
        return limit;
    }

    private void goToFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        inMemory.zoneToGo.stream()
                .filter(zone -> zone.resources > 0)
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory).reversed())
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
    }
}
