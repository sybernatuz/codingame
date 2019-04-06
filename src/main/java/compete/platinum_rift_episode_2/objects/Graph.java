package main.java.compete.platinum_rift_episode_2.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<Zone, List<Zone>> zonesByLinkedZone;

    public Graph() {
        zonesByLinkedZone = new HashMap<>();
    }
}
