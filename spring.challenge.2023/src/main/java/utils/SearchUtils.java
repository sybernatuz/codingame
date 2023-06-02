package utils;

import objects.Zone;
import objects.ZoneType;

import java.util.List;
import java.util.stream.Stream;

public class SearchUtils {

    public static Stream<Zone> findFoods(List<Zone> zones) {
        return zones.stream()
                .filter(zone -> zone.type.equals(ZoneType.FOOD))
                .filter(zone -> zone.resources > 0);
    }

    public static Stream<Zone> findEggs(List<Zone> zones) {
        return zones.stream()
                .filter(zone -> zone.type.equals(ZoneType.EGG))
                .filter(zone -> zone.resources > 0);
    }
}
