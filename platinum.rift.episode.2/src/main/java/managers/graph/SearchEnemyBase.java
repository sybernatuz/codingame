package managers.graph;

import objects.Graph;
import objects.Zone;
import utils.ZoneUtils;

import java.util.List;

public class SearchEnemyBase extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current) {
        List<Zone> enemyPodsZones = ZoneUtils.findByEnemyPods(graph);
        Zone enemyBase = enemyPodsZones.get(0);
        return current.equals(enemyBase);
    }
}
