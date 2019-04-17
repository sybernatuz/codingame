package managers.graph;

import enums.TeamEnum;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.List;
import java.util.Optional;

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
        if (graph.pathToEnemyBase == null) {
            SearchEnemyBase searchEnemyBase = new SearchEnemyBase();
            Optional<Path> pathToEnemyBase = searchEnemyBase.bfsSearch(graph, graph.friendBase);
            pathToEnemyBase.ifPresent(path -> graph.pathToEnemyBase = path);
        }
    }
}
