package main.java.compete.platinum_rift_episode_2.utils;

import main.java.compete.platinum_rift_episode_2.objects.Graph;
import main.java.compete.platinum_rift_episode_2.objects.Zone;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZoneUtils {

    public static Map.Entry<Zone, List<Zone>> findEntryById(Graph graph, int id) {
        return graph.zonesByLinkedZone.entrySet().stream()
                .filter(entry -> entry.getKey().zoneId == id)
                .findFirst()
                .orElse(null);
    }

    public static Zone findById(Graph graph, int id) {
        return graph.zonesByLinkedZone.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(zone -> zone.zoneId == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Zone> findByFriendPods(Graph graph) {
        return graph.zonesByLinkedZone.keySet().stream()
                .filter(zone -> zone.friendPods > 0)
                .collect(Collectors.toList());
    }

}
