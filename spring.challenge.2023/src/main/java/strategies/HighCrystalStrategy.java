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

//-2988031270051414500
public class HighCrystalStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);
        goToEggs(graph, inMemory, zones);

        if (noMoreEggs(graph, zones)) {
            goToFood(graph, inMemory, zones);
        }
        return zones;
    }

    private void goToEggs(Graph graph, InMemory inMemory, List<Zone> zones) {
        int totalEggsNodes = inMemory.eggZones.size();
        long limit = Math.round(totalEggsNodes * 0.80);

        inMemory.distancesBetweenImportantZones.stream()
                .filter(distance -> graph.myBases.contains(distance.source))
                .filter(distance -> distance.target.type.equals(ZoneType.EGG))
                .sorted(Comparator.comparing(distance -> distance.value))
                .distinct()
                .limit(limit)
                .filter(distance -> distance.target.resources > 0)
                .map(distance -> distance.target)
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
                .ifPresent(path -> zones.addAll(path.zones));
    }

    private boolean noMoreEggs(Graph graph, List<Zone> zones) {
        return zones.size() == graph.myBases.size();
    }

    private void goToFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        int totalFoodNodes = inMemory.foodZones.size();
        long limit = Math.round(totalFoodNodes * 0.60);

        inMemory.distancesBetweenImportantZones.stream()
                .filter(distance -> graph.myBases.contains(distance.source))
                .filter(distance -> distance.target.type.equals(ZoneType.FOOD))
                .sorted(Comparator.comparing(distance -> distance.value))
                .distinct()
                .limit(limit)
                .filter(distance -> distance.target.resources > 0)
                .map(distance -> distance.target)
                .forEach(foodZone -> searchBestPathFromComputedZone(foodZone, graph, zones));
    }
}
