package strategies;

import inmemory.InMemory;
import objects.*;
import singleton.Beans;
import utils.ComparatorUtils;
import utils.SearchUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//seed=-5556325977528636000
public class BlitzkriegStrategy {

    public List<Zone> process(Graph graph, InMemory inMemory) {
        List<Zone> zones = new ArrayList<>(graph.myBases);

        focusEggs(graph, inMemory, zones);
        focusContestedFood(graph, inMemory, zones);
        if (noMoreEggs(inMemory)) {
            focusFood(graph, inMemory, zones);
        }

        return zones;
    }

    private void focusContestedFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        SearchUtils.findFoods(inMemory.zoneToGo)
                .filter(zone -> Side.CONTESTED.equals(inMemory.locations.get(zone).side))
                .peek(zone -> zone.strength = 15)
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void focusFood(Graph graph, InMemory inMemory, List<Zone> zones) {
        inMemory.foodZones.stream()
                .filter(zone -> inMemory.locations.get(zone).side.equals(Side.MY_SIDE))
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory).reversed())
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void focusEggs(Graph graph, InMemory inMemory, List<Zone> zones) {
        SearchUtils.findEggs(inMemory.zoneToGo)
                .sorted(ComparatorUtils.minimumZoneDistance(inMemory))
                .forEach(eggZone -> searchBestPathFromComputedZone(eggZone, graph, zones));
    }

    private void searchBestPathFromComputedZone(Zone zone, Graph graph, List<Zone> zones) {
        if (zones.contains(zone))
            return ;
        zones.stream()
                .map(zoneAlreadyTargeted -> Beans.searchSpecificZone.search(graph, zoneAlreadyTargeted, zone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(path -> path.zones.size()))
                .filter(path -> path.zones.stream().noneMatch(pathZone -> graph.enemyBases.contains(pathZone)))
                .ifPresent(path -> {
                    setStrength(path, zone);
                    zones.addAll(path.zones);
                });
    }

    private void setStrength(Path path, Zone zone) {
        if (zone.strength == null)
            return;
        path.zones.forEach(z -> z.strength = zone.strength);
    }

    private boolean noMoreEggs(InMemory inMemory) {
        return inMemory.zoneToGo.stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .noneMatch(zone -> zone.resources > 0);
    }
}
