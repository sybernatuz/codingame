package main.java.compete.platinum_rift_episode_2.managers;

import main.java.compete.platinum_rift_episode_2.objects.Graph;
import main.java.compete.platinum_rift_episode_2.objects.Path;
import main.java.compete.platinum_rift_episode_2.objects.Zone;
import main.java.compete.platinum_rift_episode_2.utils.ZoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

public class ZoneManager {


    public void updateZone(Graph graph, int zoneId, Scanner in, int friendTeam) {
        Zone zoneData = new Zone(in, true, friendTeam);
        graph.zonesByLinkedZone.entrySet().stream()
                .flatMap(entry -> Stream.of(
                        Stream.of(entry.getKey()),
                        entry.getValue().stream()))
                .flatMap(Function.identity())
                .filter(zone -> zone.zoneId == zoneId)
                .forEach(zone -> zone.update(zoneData));
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
