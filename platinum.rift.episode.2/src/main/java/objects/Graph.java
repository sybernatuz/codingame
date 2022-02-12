package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<Zone, List<Zone>> zonesByLinkedZone;
    public Zone friendBase;
    public Zone enemyBase;

    public Graph() {
        zonesByLinkedZone = new HashMap<>();
    }
}
