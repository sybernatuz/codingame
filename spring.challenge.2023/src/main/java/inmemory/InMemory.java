package inmemory;

import objects.*;
import singleton.Beans;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemory {

    public int totalCrystals;
    public int totalEggs;
    public List<Zone> foodZones;
    public List<Zone> eggZones;
    public Map<Zone, Location> locations = new HashMap<>();
    public List<Zone> zoneToGo = new ArrayList<>();
    public List<Distance> distances = new ArrayList<>();
    public Integer turn = 0;
    public Integer step = 1;
    public Integer myUnits;
    public Integer enemyUnits;


    public InMemory(Graph graph) {
        eggZones = graph.zones.values().stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .collect(Collectors.toList());
        foodZones = graph.zones.values().stream()
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .collect(Collectors.toList());

        totalCrystals = foodZones.stream()
                .mapToInt(zone -> zone.resources)
                .sum();
        totalEggs = eggZones.stream()
                .mapToInt(zone -> zone.resources)
                .sum();



        List<Distance> distances = graph.zones.values().stream()
                .flatMap(zone -> computeDistanceBetweenBases(zone, graph).stream())
                .collect(Collectors.toList());
        this.distances.addAll(distances);

        zoneToGo.addAll(Beans.nodesToGoComputer.compute(this));
    }

    private List<Distance> computeDistanceBetweenBases(Zone zone, Graph graph) {
        return Stream.concat(graph.myBases.stream(), graph.enemyBases.stream())
                .map(myBase -> findDistance(graph, myBase, zone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Distance> findDistance(Graph graph, Zone source, Zone target) {
        return Beans.searchSpecificZone.search(graph, source, target)
                .map(path ->  {
                    Distance distance = new Distance();
                    distance.value = path.zones.size();
                    distance.source = source;
                    distance.target = target;
                    return distance;
                });
    }

    public void update(Graph graph) {
        myUnits = graph.zones.values().stream()
                .mapToInt(zone -> zone.myAnts)
                .sum();
        enemyUnits = graph.zones.values().stream()
                .mapToInt(zone -> zone.oppAnts)
                .sum();
    }
}
