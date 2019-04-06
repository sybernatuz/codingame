package main.java.compete.platinum_rift_episode_2.utils;

import main.java.compete.platinum_rift_episode_2.objects.Zone;

import java.util.List;

public class ZoneUtils {

    public static Zone findById(List<Zone> zones, int id) {
        return zones.stream()
                .filter(zone -> zone.zoneId == id)
                .findFirst()
                .orElse(null);
    }
}
