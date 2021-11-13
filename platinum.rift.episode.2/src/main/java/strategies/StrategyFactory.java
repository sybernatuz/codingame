package strategies;

import managers.graph.search.BfsSearch;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Path;

import java.util.Optional;

public class StrategyFactory {

    private final Strategy moveStrategy = new BasicStrategy();
    private final Strategy rushStrategy = new RushStrategy();
    private final BfsSearch searchEnemyBase = new SearchEnemyBase();

    public Strategy getStrategy(Graph graph) {
        if (isEnemyBaseClose(graph))
            return rushStrategy;
        return moveStrategy;
    }

    private boolean isEnemyBaseClose(Graph graph) {
        Optional<Path> pathToEnemyBase = searchEnemyBase.search(graph, graph.friendBase);
        return pathToEnemyBase.filter(path -> path.zones.size() <= 5).isPresent();
    }
}
