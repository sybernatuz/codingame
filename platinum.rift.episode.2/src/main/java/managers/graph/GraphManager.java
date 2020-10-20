package managers.graph;

import enums.TeamEnum;
import objects.Graph;
import objects.Zone;
import utils.ZoneUtils;

import java.util.List;

public class GraphManager {

    public void initGraphData(Graph graph) {
        if (graph.friendBase == null) {
            List<Zone> friendZones = ZoneUtils.findByTeam(graph, TeamEnum.FRIEND);
            graph.friendBase = friendZones.get(0);
        }
        if (graph.enemyBase == null) {
            List<Zone> enemyZones = ZoneUtils.findByTeam(graph, TeamEnum.ENEMY);
            graph.enemyBase = enemyZones.get(0);
        }
    }
}
