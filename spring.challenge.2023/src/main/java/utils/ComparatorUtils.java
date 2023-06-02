package utils;

import inmemory.InMemory;
import objects.Zone;

import java.util.Comparator;

public class ComparatorUtils {

    public static Comparator<Zone> minimumZoneDistance(InMemory inMemory) {
        return Comparator.comparing((Zone zone) -> inMemory.locations.get(zone).distanceFromClosestMyBase);
    }
}
