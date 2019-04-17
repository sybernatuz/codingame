package initializers;

import objects.Graph;
import objects.Zone;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphInitializer {


    /*
                                                                  Friend zone 3
                                                                       |
                                                                  Friend zone 2
                                                                       |
                None zone 2 --- Friend zone 1 --- Friend base --- None zone 1 --- Enemy base

     */
    public static Graph initGraph() {
        Graph graph = new Graph();
        graph.zonesByLinkedZone = createZonesByLinkedZone();
        graph.enemyBase = ZoneInitializer.initEnemyBase();
        graph.friendBase = ZoneInitializer.initFriendBase();
        return graph;
    }

    private static Map<Zone, List<Zone>> createZonesByLinkedZone() {
        Map<Zone, List<Zone>> zonesByLinkedZone = new HashMap<>();
        List<Zone> neighboursFriendBase = Arrays.asList(
                ZoneInitializer.initFriendZone1(),
                ZoneInitializer.initNoneZone1()
        );
        zonesByLinkedZone.put(ZoneInitializer.initFriendBase(), neighboursFriendBase);
        List<Zone> neighboursFriendZone1 = Arrays.asList(
                ZoneInitializer.initFriendBase(),
                ZoneInitializer.initNoneZone2()
        );
        zonesByLinkedZone.put(ZoneInitializer.initFriendZone1(), neighboursFriendZone1);
        List<Zone> neighboursNoneZone1 = Arrays.asList(
                ZoneInitializer.initFriendBase(),
                ZoneInitializer.initEnemyBase(),
                ZoneInitializer.initFriendZone2()
        );
        zonesByLinkedZone.put(ZoneInitializer.initNoneZone1(), neighboursNoneZone1);
        List<Zone> neighboursNoneZone2 = Collections.singletonList(
                ZoneInitializer.initFriendZone1()
        );
        zonesByLinkedZone.put(ZoneInitializer.initNoneZone2(), neighboursNoneZone2);
        List<Zone> neighboursEnemyBase = Collections.singletonList(
                ZoneInitializer.initNoneZone1()
        );
        zonesByLinkedZone.put(ZoneInitializer.initEnemyBase(), neighboursEnemyBase);
        List<Zone> neighboursFriendZone2 = Arrays.asList(
                ZoneInitializer.initNoneZone1(),
                ZoneInitializer.initFriendZone3()
        );
        zonesByLinkedZone.put(ZoneInitializer.initFriendZone2(), neighboursFriendZone2);
        List<Zone> neighboursFriendZone3 = Collections.singletonList(
                ZoneInitializer.initFriendZone2()
        );
        zonesByLinkedZone.put(ZoneInitializer.initFriendZone3(), neighboursFriendZone3);
        return zonesByLinkedZone;
    }
}
