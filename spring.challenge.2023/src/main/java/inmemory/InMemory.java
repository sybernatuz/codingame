package inmemory;

import objects.*;
import singleton.Beans;

import java.util.*;
import java.util.stream.Collectors;

public class InMemory {

    public Map<Zone, Path> pathToClosestEggFromBase = new HashMap<>();
    public int totalCrystals;
    public List<Zone> foodZones;
    public List<Zone> eggZones;
    public List<Distance> distancesBetweenImportantZones = new ArrayList<>();
    public Integer turn = 0;


    public InMemory(Graph graph) {
        graph.myBases.forEach(base -> {
            Beans.searchClosestEgg.search(graph, base)
                    .ifPresent(cell -> pathToClosestEggFromBase.put(base, cell));
        });

        totalCrystals = graph.zones.values().stream()
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .mapToInt(zone -> zone.resources)
                .sum();

        foodZones = graph.zones.values().stream()
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .collect(Collectors.toList());

        eggZones = graph.zones.values().stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .collect(Collectors.toList());


        List<Distance> foodDistances = foodZones.stream()
                .flatMap(foodZone -> computeDistanceBetweenBases(foodZone, graph).stream())
                .collect(Collectors.toList());
        distancesBetweenImportantZones.addAll(foodDistances);
        List<Distance> eggDistances = eggZones.stream()
                .flatMap(eggZone -> computeDistanceBetweenBases(eggZone, graph).stream())
                .collect(Collectors.toList());
        distancesBetweenImportantZones.addAll(eggDistances);


    }

    private List<Distance> computeDistanceBetweenBases(Zone foodZone, Graph graph) {
        List<Distance> distances = new ArrayList<>();
        graph.myBases.stream()
                .map(myBase -> findDistance(graph, myBase, foodZone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(distances::add);
        graph.enemyBases.stream()
                .map(enemyBase -> findDistance(graph, enemyBase, foodZone))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(distances::add);
        return distances;
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
}
