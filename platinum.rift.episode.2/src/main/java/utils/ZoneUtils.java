package utils;


import enums.TeamEnum;
import objects.Graph;
import objects.Zone;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZoneUtils {

    public static Zone findById(Graph graph, int id) {
        return graph.zonesByLinkedZone.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(zone -> zone.id == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Zone> findByFriendPods(Graph graph) {
        return graph.zonesByLinkedZone.keySet().stream()
                .filter(zone -> zone.friendPods > 0)
                .collect(Collectors.toList());
    }

    public static List<Zone> findByEnemyPods(Graph graph) {
        return graph.zonesByLinkedZone.keySet().stream()
                .filter(zone -> zone.enemyPods > 0)
                .collect(Collectors.toList());
    }

    public static List<Zone> findByTeam(Graph graph, TeamEnum team) {
        return graph.zonesByLinkedZone.keySet().stream()
                .filter(zone -> zone.team.equals(team))
                .collect(Collectors.toList());
    }

}
