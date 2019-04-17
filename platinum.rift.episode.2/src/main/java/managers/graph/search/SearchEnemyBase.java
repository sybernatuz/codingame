package managers.graph.search;

import objects.Graph;
import objects.Zone;

public class SearchEnemyBase extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current) {
        return current.equals(graph.enemyBase);
    }
}
