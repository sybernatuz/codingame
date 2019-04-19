package strategies;


import enums.TeamEnum;
import managers.graph.search.SearchClosestNotFriendZone;
import managers.graph.search.SearchClosestPlatinumSource;
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

public class MoveStrategy {

    private SearchClosestPlatinumSource searchClosestPlatinumSource = new SearchClosestPlatinumSource();
    private SearchClosestNotFriendZone  searchClosestNotFriendZone  = new SearchClosestNotFriendZone();

    public List<Move> computeMoves(Graph graph) {
        List<Move> moves = new ArrayList<>();
        List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);

        Random random = new Random();
        friendPodsZones.forEach(friendPodsZone -> computeMovesByUnit(graph, friendPodsZone, random, moves));
        return moves;
    }

    private void computeMovesByUnit(Graph graph, Zone friendPodsZone, Random random, List<Move> moves) {
        int unitsToMove = computeUnitsToMove(graph, friendPodsZone);
        for (int i = 0; i < unitsToMove; i++) {
            Move currentMove = createMove(friendPodsZone, graph, random);

            moves.stream()
                    .filter(move -> move.equals(currentMove))
                    .findFirst()
                    .ifPresentOrElse(
                            move -> move.number++,
                            () -> moves.add(currentMove)
                    );
        }
    }

    private Move createMove(Zone friendPodsZone, Graph graph, Random random) {
        Move currentMove = new Move();
        currentMove.zoneSource = friendPodsZone;
        currentMove.number = 1;
        currentMove.zoneTarget = computeZoneTarget(random, friendPodsZone, graph);
        return currentMove;
    }

    private int computeUnitsToMove(Graph graph, Zone zone) {
        int unitsToMove = zone.friendPods;
        if (zone.equals(graph.friendBase))
            unitsToMove--;
        return unitsToMove;
    }

    private Zone computeZoneTarget(Random random, Zone currentZone, Graph graph) {
        List<Zone> neighbours = graph.zonesByLinkedZone.get(currentZone);
        return getRandomNotOwnedZone(neighbours, random)
                .orElse(getClosestPlatinumZone(graph, currentZone)
                .orElse(getClosestNotFriendZone(graph, currentZone)
                .orElse(getByPathToEnemyBase(graph, currentZone)
                .orElse(getByRandomNeighbour(neighbours, random)
                .orElse(null)))));
    }

    private Optional<Zone> getClosestPlatinumZone(Graph graph, Zone currentZone) {
        Optional<Path> pathToClosestPlatinumSource = searchClosestPlatinumSource.bfsSearch(graph, currentZone);
        return pathToClosestPlatinumSource.map(path -> path.zones.get(0));
    }

    private Optional<Zone> getClosestNotFriendZone(Graph graph, Zone currentZone) {
        Optional<Path> pathToClosestNotFriendZone = searchClosestNotFriendZone.bfsSearch(graph, currentZone);
        return pathToClosestNotFriendZone.map(path -> path.zones.get(0));
    }

    private Optional<Zone> getRandomNotOwnedZone(List<Zone> neighbours, Random random) {
        List<Zone> notFriendNeighbours = neighbours.stream()
                .filter(neighbour -> !neighbour.team.equals(TeamEnum.FRIEND))
                .collect(Collectors.toList());
        if (notFriendNeighbours.isEmpty())
            return Optional.empty();
        int randomZone = random.nextInt(notFriendNeighbours.size());
        return Optional.of(notFriendNeighbours.get(randomZone));
    }

    private Optional<Zone> getByPathToEnemyBase(Graph graph, Zone currentZone) {
        if (!graph.pathToEnemyBase.zones.contains(currentZone) || currentZone.equals(graph.friendBase))
            return Optional.empty();
        int indexOfNextZone = graph.pathToEnemyBase.zones.indexOf(currentZone) + 1;
        if (indexOfNextZone >= graph.pathToEnemyBase.zones.size())
            return Optional.empty();
        return Optional.of(graph.pathToEnemyBase.zones.get(indexOfNextZone));
    }

    private Optional<Zone> getByRandomNeighbour(List<Zone> neighbours, Random random) {
        int randomNeighbour = random.nextInt(neighbours.size());
        return Optional.of(neighbours.get(randomNeighbour));
    }
}
