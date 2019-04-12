package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<Zone, List<Zone>> zonesByLinkedZone;
    public Map<SourceTarget, List<List<Path>>> sourceTargetPaths;
    public Zone friendBase;
    public Path pathToEnemyBase;

    public Graph() {
        zonesByLinkedZone = new HashMap<>();
        sourceTargetPaths = new HashMap<>();
    }
}
