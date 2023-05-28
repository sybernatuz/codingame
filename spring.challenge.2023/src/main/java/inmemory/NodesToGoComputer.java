package inmemory;

import objects.*;
import utils.LogsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodesToGoComputer {


    public List<Zone> compute(InMemory inMemory) {
        dispatchSide(inMemory);

        List<Zone> zoneToGo = new ArrayList<>();
        zoneToGo.addAll(mySideZones(inMemory));
        zoneToGo.addAll(contestedZones(inMemory));
        zoneToGo.addAll(possibleEnemyZones(inMemory));

        zoneToGo.forEach(zone -> LogsUtils.log("Zone OK %s", zone.index));
        return zoneToGo;

    }

    private void dispatchSide(InMemory inMemory) {
        inMemory.distancesBetweenImportantZones.stream()
                .collect(Collectors.groupingBy(distance -> distance.target))
                .forEach((resourceZone, distances) -> {
                    int closestMyBase = distances.stream()
                            .filter(distance -> distance.source.type.equals(ZoneType.MY_BASE))
                            .mapToInt(distance -> distance.value)
                            .min()
                            .orElseThrow(RuntimeException::new);
                    int closestEnemyBases = distances.stream()
                            .filter(distance -> distance.source.type.equals(ZoneType.ENEMY_BASE))
                            .mapToInt(distance -> distance.value)
                            .min()
                            .orElseThrow(RuntimeException::new);


                    Location location = new Location();
                    if (closestMyBase < closestEnemyBases) {
                        location.side = Side.MY_SIDE;
                        location.distanceDifferenceWithOppositeBase = closestEnemyBases - closestMyBase;
                    } else if (closestMyBase == closestEnemyBases) {
                        location.side = Side.CONTESTED;
                        location.distanceDifferenceWithOppositeBase = 0;
                    } else {
                        location.side = Side.ENEMY_SIDE;
                        location.distanceDifferenceWithOppositeBase = closestMyBase - closestEnemyBases;
                    }
                    location.distanceFromClosestMyBase = closestMyBase;
                    location.distanceFromClosestEnemyBase = closestEnemyBases;
                    inMemory.locations.put(resourceZone, location);
                });
    }

    private List<Zone> mySideZones(InMemory inMemory) {
        return inMemory.locations.entrySet().stream()
                .filter(entry -> entry.getValue().side.equals(Side.MY_SIDE))
                .peek(entry -> LogsUtils.log("My side zone %s", entry.getKey().index))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Zone> contestedZones(InMemory inMemory) {
        return inMemory.locations.entrySet().stream()
                .filter(entry -> entry.getValue().side.equals(Side.CONTESTED))
                .peek(entry -> LogsUtils.log("Contested zone %s", entry.getKey().index))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Zone> possibleEnemyZones(InMemory inMemory) {
        return inMemory.locations.entrySet().stream()
                .filter(entry -> entry.getValue().side.equals(Side.ENEMY_SIDE))
                .peek(entry -> LogsUtils.log("Enemy zone %s", entry.getKey().index))
                .filter(entry -> entry.getValue().distanceDifferenceWithOppositeBase <= 2)
                .filter(entry -> entry.getValue().distanceFromClosestEnemyBase > 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
