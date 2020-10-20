package strategies;


import enums.TeamEnum;
import managers.graph.search.BfsSearch;
import managers.graph.search.SearchClosestNotFriendZone;
import managers.graph.search.SearchClosestPlatinumSource;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Move;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoveStrategy {

    private final BfsSearch searchClosestPlatinumSource = new SearchClosestPlatinumSource();
    private final BfsSearch  searchClosestNotFriendZone  = new SearchClosestNotFriendZone();
    private final BfsSearch searchEnemyBase = new SearchEnemyBase();
    private final Random random = new Random();

    public List<Move> computeMoves(Graph graph) {
        List<Move> moves = new ArrayList<>();
        List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);

        friendPodsZones.forEach(friendPodsZone -> computeMovesByUnit(graph, friendPodsZone, moves));
        return moves;
    }

    private void computeMovesByUnit(Graph graph, Zone friendPodsZone, List<Move> moves) {
        int unitsToMove = computeUnitsToMove(graph, friendPodsZone);
        IntStream.range(0, unitsToMove)
                .forEach(index ->  {
                    Move currentMove = createMove(friendPodsZone, graph);

                    Optional<Move> opt = moves.stream()
                            .filter(move -> move.equals(currentMove))
                            .findFirst();
                    if (opt.isPresent())
                        opt.get().number++;
                    else
                        moves.add(currentMove);
                });
    }

    private Move createMove(Zone friendPodsZone, Graph graph) {
        Move currentMove = new Move();
        currentMove.zoneSource = friendPodsZone;
        currentMove.number = 1;
        currentMove.zoneTarget = computeZoneTarget(friendPodsZone, graph);
        return currentMove;
    }

    private int computeUnitsToMove(Graph graph, Zone zone) {
        int unitsToMove = zone.friendPods;
        if (zone.equals(graph.friendBase))
            unitsToMove--;
        return unitsToMove;
    }

    private Zone computeZoneTarget(Zone currentZone, Graph graph) {
        List<Zone> neighbours = graph.zonesByLinkedZone.get(currentZone);
        return getRandomNotOwnedZone(neighbours)
                .orElse(getWithBfs(searchClosestPlatinumSource, graph, currentZone)
                .orElse(getWithBfs(searchClosestNotFriendZone, graph, currentZone)
                .orElse(getWithBfs(searchEnemyBase, graph, currentZone)
                .orElse(getByRandomNeighbour(neighbours)
                .orElse(null)))));
    }

    private Optional<Zone> getWithBfs(BfsSearch bfsSearch, Graph graph, Zone currentZone) {
        Optional<Path> pathToClosestPlatinumSource = bfsSearch.search(graph, currentZone);
        return pathToClosestPlatinumSource.map(path -> path.zones.get(0));
    }

    private Optional<Zone> getRandomNotOwnedZone(List<Zone> neighbours) {
        List<Zone> notFriendNeighbours = neighbours.stream()
                .filter(neighbour -> !neighbour.team.equals(TeamEnum.FRIEND))
                .collect(Collectors.toList());
        if (notFriendNeighbours.isEmpty())
            return Optional.empty();
        int randomZone = random.nextInt(notFriendNeighbours.size());
        return Optional.of(notFriendNeighbours.get(randomZone));
    }

    private Optional<Zone> getByRandomNeighbour(List<Zone> neighbours) {
        int randomNeighbour = random.nextInt(neighbours.size());
        return Optional.of(neighbours.get(randomNeighbour));
    }
}
