package strategies;

import inmemory.InMemory;
import objects.Action;
import objects.Graph;
import objects.Zone;
import singleton.Beans;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StrategiesBridge {


    public List<Action> computeActions(Graph graph, InMemory inMemory) {
        if (graph.myBases.size() > 1) {
            removeUselessBase(graph, inMemory);
        }

        return getZones(graph, inMemory).stream()
                .map(this::mapZoneToAction)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Zone> getZones(Graph graph, InMemory inMemory) {
        List<Zone> zones;
        if (inMemory.eggZones.size() == 2) {
            zones = Beans.blitzkriegStrategy.process(graph, inMemory);
        } else if (inMemory.totalCrystals < 100) {
            zones = Beans.lowCrystalStrategy.goToAllFood(graph, inMemory);
        } else if (inMemory.totalCrystals >= 4000) {
            zones = Beans.highCrystalStrategy.process(graph, inMemory);
        } else {
//            zones = Beans.mainStrategy.process(graph, inMemory);
            zones = Beans.eggsStrategy.process(graph, inMemory);
        }

        removeBaseWithNoLinks(graph, zones);
        return zones;
    }

    private void removeBaseWithNoLinks(Graph graph, List<Zone> zones) {
        graph.myBases.stream()
                .filter(base -> graph.graph.get(base).stream().noneMatch(zones::contains))
                .findFirst()
                .ifPresent(zones::remove);
    }

    private Action mapZoneToAction(Zone zone) {
        Action action = new Action(Action.Type.BEACON);
        action.strength = Optional.ofNullable(zone.strength)
                .orElse(10);
        action.index1 = zone.index;
        return action;
    }

    private void removeUselessBase(Graph graph, InMemory inMemory) {
        Zone myBase1 = graph.myBases.get(0);
        Zone myBase2 = graph.myBases.get(1);
        boolean uselessBase1 = Stream.concat(inMemory.foodZones.stream(), inMemory.eggZones.stream())
                .filter(zone -> zone.resources > 0)
                .allMatch(zone -> findDistance(inMemory, myBase1, zone) >= findDistance(inMemory, myBase2, zone));
        boolean uselessBase2 = Stream.concat(inMemory.foodZones.stream(), inMemory.eggZones.stream())
                .filter(zone -> zone.resources > 0)
                .allMatch(zone -> findDistance(inMemory, myBase2, zone) >= findDistance(inMemory, myBase1, zone));
        if (uselessBase1) {
            graph.myBases.remove(0);
        }
        if (uselessBase2) {
            graph.myBases.remove(1);
        }
    }

    private int findDistance(InMemory inMemory, Zone source, Zone target) {
        return inMemory.distances.stream()
                .filter(distance -> distance.source.equals(source))
                .filter(distance -> distance.target.equals(target))
                .map(distance -> distance.value)
                .findFirst()
                .orElse(0);
    }
}
