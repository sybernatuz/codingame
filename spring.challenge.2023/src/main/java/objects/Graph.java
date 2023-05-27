package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public List<Zone> myBases = new ArrayList<>();
    public List<Zone> enemyBases = new ArrayList<>();
    public Map<Integer, Zone> zones;
    public Map<Zone, List<Zone>> graph = new HashMap<>();

    public Graph(Map<Integer, Zone> zones) {
        this.zones = zones;
        init();
    }

    private void init() {
        zones.values()
                .forEach(zone -> {
                    List<Zone> neighbours = new ArrayList<>();
                    if (zone.neigh0 >= 0)
                        neighbours.add(zones.get(zone.neigh0));
                    if (zone.neigh1 >= 0)
                        neighbours.add(zones.get(zone.neigh1));
                    if (zone.neigh2 >= 0)
                        neighbours.add(zones.get(zone.neigh2));
                    if (zone.neigh3 >= 0)
                        neighbours.add(zones.get(zone.neigh3));
                    if (zone.neigh4 >= 0)
                        neighbours.add(zones.get(zone.neigh4));
                    if (zone.neigh5 >= 0)
                        neighbours.add(zones.get(zone.neigh5));
                    this.graph.put(zone, neighbours);
                });
    }
}
