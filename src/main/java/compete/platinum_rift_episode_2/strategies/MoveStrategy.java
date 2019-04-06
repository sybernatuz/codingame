package main.java.compete.platinum_rift_episode_2.strategies;

import main.java.compete.platinum_rift_episode_2.enums.TeamEnum;
import main.java.compete.platinum_rift_episode_2.objects.Graph;
import main.java.compete.platinum_rift_episode_2.objects.Move;
import main.java.compete.platinum_rift_episode_2.objects.Path;
import main.java.compete.platinum_rift_episode_2.objects.Zone;
import main.java.compete.platinum_rift_episode_2.utils.ZoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MoveStrategy {

    public List<Move> computeMoves(Graph graph) {
        List<Move> moves = new ArrayList<>();
        List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);
        List<Zone> platinumNotFriendZones = ZoneUtils.findByPlatinumSourceAndExcludeTeam(graph, TeamEnum.FRIEND);
        Random random = new Random();
        for (Zone zone : friendPodsZones) {
            int randomZone = random.nextInt(platinumNotFriendZones.size() - 1);
            Supplier findByZone = () -> zone.equals(platinumNotFriendZones.get(randomZone));
            List<Path> paths = ZoneUtils.findAllPathsToDestination(graph, zone, findByZone);
            int randomPath = random.nextInt(paths.size() - 1);
            Path path = paths.get(randomPath);
            Move move = new Move();
            move.number = zone.friendPods;
            move.zoneSource = zone;
            move.zoneTarget = path.zones.get(0);
            moves.add(move);
        }
        return moves;
    }
}
