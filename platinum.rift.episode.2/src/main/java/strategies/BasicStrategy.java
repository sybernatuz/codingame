package strategies;


import enums.TeamEnum;
import managers.graph.search.BfsSearch;
import managers.graph.search.SearchClosestNotFriendZone;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Move;
import objects.Path;
import objects.Zone;
import utils.ZoneUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BasicStrategy implements Strategy {

    private final BfsSearch  searchClosestNotFriendZone  = new SearchClosestNotFriendZone();
    private final BfsSearch searchEnemyBase = new SearchEnemyBase();
    private final Random random = new Random();

    @Override public List<Move> computeMoves(Graph graph) {
        List<Move> moves = new ArrayList<>();
        List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);

        friendPodsZones.forEach(friendPodsZone -> computeMovesByUnit(graph, friendPodsZone, moves));
        return moves;
    }

    private void computeMovesByUnit(Graph graph, Zone friendPodsZone, List<Move> moves) {
        int unitsToMove = computeUnitsToMove(graph, friendPodsZone);
        IntStream.range(0, unitsToMove).forEach(index ->  computeMove(graph, friendPodsZone, moves));
    }

    private void computeMove(Graph graph, Zone friendPodsZone, List<Move> moves) {
        Zone target = computeZoneTarget(friendPodsZone, graph);
        Optional<Move> existingMove = moves.stream()
                .filter(move -> move.zoneTarget.equals(target) && move.zoneSource.equals(friendPodsZone))
                .findFirst();

        if (!existingMove.isPresent()) {
            moves.add(createNewMove(friendPodsZone, target));
            return;
        }

        if (existingMove.get().number >= 3) {
            getWithBfs(searchEnemyBase, graph, friendPodsZone).ifPresent(zoneToEnemyBase -> moves.add(createNewMove(friendPodsZone, zoneToEnemyBase)));
            return;
        }
        existingMove.get().number++;
    }

    private Move createNewMove(Zone friendPodsZone, Zone target) {
        Move currentMove = new Move();
        currentMove.zoneSource = friendPodsZone;
        currentMove.number = 1;
        currentMove.zoneTarget = target;
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
        return getNotOwnedPlatinumZone(neighbours)
                .orElseGet(() -> getRandomNotOwnedZone(neighbours)
                .orElseGet(() -> getWithBfs(searchClosestNotFriendZone, graph, currentZone)
                .orElseGet(() -> getWithBfs(searchEnemyBase, graph, currentZone)
                .orElseGet(() -> getByRandomNeighbour(neighbours)))));
    }

    private Optional<Zone> getWithBfs(BfsSearch bfsSearch, Graph graph, Zone currentZone) {
        Optional<Path> pathToClosestPlatinumSource = bfsSearch.search(graph, currentZone);
        return pathToClosestPlatinumSource.map(path -> path.zones.get(0));
    }

    private Optional<Zone> getNotOwnedPlatinumZone(List<Zone> neighbours) {
        return neighbours.stream()
                .filter(neighbour -> !neighbour.team.equals(TeamEnum.FRIEND))
                .filter(notFriendNeighbour -> notFriendNeighbour.platinum > 0)
                .findFirst();
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

    private Zone getByRandomNeighbour(List<Zone> neighbours) {
        int randomNeighbour = random.nextInt(neighbours.size());
        return neighbours.get(randomNeighbour);
    }
}
