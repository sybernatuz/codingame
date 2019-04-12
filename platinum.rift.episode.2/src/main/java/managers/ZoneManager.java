package managers;


import objects.Graph;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class ZoneManager {


    public void updateZone(Graph graph, Scanner in, int friendTeam) {
        int zoneId = in.nextInt();
        Zone zoneData = new Zone(in, true, friendTeam);
        graph.zonesByLinkedZone.entrySet().stream()
                .flatMap(entry -> Stream.of(
                        Stream.of(entry.getKey()),
                        entry.getValue().stream()))
                .flatMap(Function.identity())
                .filter(zone -> zone.zoneId == zoneId)
                .forEach(zone -> zone.update(zoneData));
    }

    public void initShortestPathToEnemyBase(Graph graph) {
        if (graph.friendBase != null || graph.pathToEnemyBase != null)
            return;

        List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);
        List<Zone> enemyPodsZones = ZoneUtils.findByEnemyPods(graph);

        graph.friendBase = friendPodsZones.get(0);

        Zone friendBase = friendPodsZones.get(0);
        Zone enemyBase = enemyPodsZones.get(0);
        LinkedList<Zone> bfsList = new LinkedList<>();
        Queue<Zone> queue = new LinkedList<>();
        Map<Zone, Zone> prev = new HashMap<>();
        Zone current = friendBase;

        queue.add(current);
        current.isVisited = true;

        while (!queue.isEmpty()) {

            current = queue.remove();

            if (current.equals(enemyBase))
                break;
            else {
                List<Zone> currentNeighbours = graph.zonesByLinkedZone.get(current);
                for (Zone currentNeighbour : currentNeighbours) {
                    if (!currentNeighbour.isVisited) {
                        queue.add(currentNeighbour);
                        currentNeighbour.isVisited = true;
                        prev.put(currentNeighbour, current);
                    }
                }
            }
        }

        if (!current.equals(enemyBase))
            return;

        for (Zone node = enemyBase; node != null; node = prev.get(node))
            bfsList.add(node);

        Collections.reverse(bfsList);
        Path path = new Path();
        path.zones = bfsList;
        graph.pathToEnemyBase = path;
    }

    public void organizeZonesLink(Scanner in, Graph graph) {
        int zone1Id = in.nextInt();
        int zone2Id = in.nextInt();
        Zone zone1 = ZoneUtils.findById(graph, zone1Id);
        Zone zone2 = ZoneUtils.findById(graph, zone2Id);
        organizeZoneLink(zone1, zone2, graph);
        organizeZoneLink(zone2, zone1, graph);
    }


    private void organizeZoneLink(Zone zone1, Zone zone2, Graph graph) {
        graph.zonesByLinkedZone.entrySet().stream()
                .filter(entry -> zone1.equals(entry.getKey()))
                .findFirst()
                .ifPresent(
                    entry -> entry.getValue().stream()
                        .filter(zone -> zone.equals(zone2))
                        .findFirst()
                        .ifPresentOrElse(
                                zone -> {},
                                () -> entry.getValue().add(zone2)
                        )
                );
    }



}
