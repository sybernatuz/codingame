package initializers;

import objects.Graph;
import objects.Zone;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphInitializer {

    public static Graph initGraph() {
        Graph graph = new Graph();
        Map<Zone, List<Zone>> zoneListMap = new HashMap<>();
        List<Zone> neighboursFriendZone1 = Arrays.asList(
                ZoneInitializer.initFriendZone2(),
                ZoneInitializer.initNoneZone1()
        );
        zoneListMap.put(ZoneInitializer.initFriendZone1(), neighboursFriendZone1);
        List<Zone> neighboursFriendZone2 = Arrays.asList(
                ZoneInitializer.initFriendZone1(),
                ZoneInitializer.initNoneZone2()
        );
        zoneListMap.put(ZoneInitializer.initFriendZone2(), neighboursFriendZone2);
        List<Zone> neighboursNoneZone1 = Arrays.asList(
                ZoneInitializer.initFriendZone1(),
                ZoneInitializer.initEnemyZone1()
        );
        zoneListMap.put(ZoneInitializer.initNoneZone1(), neighboursNoneZone1);
        List<Zone> neighboursNoneZone2 = Collections.singletonList(
                ZoneInitializer.initFriendZone2()
        );
        zoneListMap.put(ZoneInitializer.initNoneZone2(), neighboursNoneZone2);
        List<Zone> neighboursEnemyZone1 = Collections.singletonList(
                ZoneInitializer.initNoneZone1()
        );
        zoneListMap.put(ZoneInitializer.initEnemyZone1(), neighboursEnemyZone1);
        graph.zonesByLinkedZone = zoneListMap;
        return graph;
    }
}
