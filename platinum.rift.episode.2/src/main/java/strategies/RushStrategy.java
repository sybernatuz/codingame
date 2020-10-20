package strategies;

import managers.graph.search.BfsSearch;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Move;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RushStrategy implements Strategy {

    private final BfsSearch bfsSearch = new SearchEnemyBase();

    @Override public List<Move> computeMoves(Graph graph) {
        Zone zoneWithAllFriendPods = ZoneUtils.findByFriendPods(graph).get(0);
        Optional<Path> pathToEnemyBase = bfsSearch.search(graph, zoneWithAllFriendPods);
        return pathToEnemyBase.map(path -> Collections.singletonList(createMove(zoneWithAllFriendPods, path))).orElse(Collections.emptyList());
    }

    private Move createMove(Zone friendPodsZone, Path pathToEnemyBase) {
        Move currentMove = new Move();
        currentMove.zoneSource = friendPodsZone;
        currentMove.number = friendPodsZone.friendPods;
        currentMove.zoneTarget = pathToEnemyBase.zones.get(0);
        return currentMove;
    }
}
