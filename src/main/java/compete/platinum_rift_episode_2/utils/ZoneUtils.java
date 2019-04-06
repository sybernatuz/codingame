package main.java.compete.platinum_rift_episode_2.utils;

import main.java.compete.platinum_rift_episode_2.enums.TeamEnum;
import main.java.compete.platinum_rift_episode_2.objects.Graph;
import main.java.compete.platinum_rift_episode_2.objects.Path;
import main.java.compete.platinum_rift_episode_2.objects.Zone;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
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

    public static List<Zone> findByPlatinumSourceAndExcludeTeam(Graph graph, TeamEnum excludeTeam) {
        return graph.zonesByLinkedZone.keySet().stream()
                .filter(zone -> zone.platinum > 0 || (!zone.isVisited))
                .filter(zone -> !zone.team.equals(excludeTeam))
                .collect(Collectors.toList());
    }

    public static List<Path> findAllPathsToDestination(Graph graph, Zone source, Supplier target) {
        List<Path> paths = new ArrayList<>();
        Path tempPath = new Path();
        findAllPathsToDestination(graph, source, target, paths, tempPath);
        return paths;
    }

    private static void findAllPathsToDestination(Graph graph, Zone source, Supplier target, List<Path> paths, Path path) {
        // Mark the current node
        source.isVisited = true;

        if ((boolean) target.get()) {
            paths.add((Path) path.clone());
            source.isVisited = false;
            return;
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Zone zone : graph.zonesByLinkedZone.get(source)) {
            if (!zone.isVisited) {
                path.zones.add(zone);
                findAllPathsToDestination(graph, zone, target, paths, path);
                path.zones.remove(zone);
            }
        }
        source.isVisited = false;
    }
}
