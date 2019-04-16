import managers.ZoneManager;
import managers.graph.SearchEnemyBase;
import objects.Graph;
import objects.Move;
import objects.Path;
import objects.Zone;
import strategies.MoveStrategy;
import utils.ZoneUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private static ZoneManager zoneManager = new ZoneManager();
    private static MoveStrategy moveStrategy = new MoveStrategy();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int playerCount = in.nextInt(); // the amount of players (always 2)
        int friendTeam = in.nextInt(); // my player ID (0 or 1)
        int zoneCount = in.nextInt(); // the amount of zones on the map
        int linkCount = in.nextInt(); // the amount of links between all zones
        Graph graph = new Graph();
        for (int i = 0; i < zoneCount; i++) {
            graph.zonesByLinkedZone.put(new Zone(in, false, friendTeam), new ArrayList<>());
        }

        for (int i = 0; i < linkCount; i++) {
            zoneManager.organizeZonesLink(in, graph);
        }

        // game loop
        while (true) {
            int myPlatinum = in.nextInt(); // your available Platinum
            for (int i = 0; i < zoneCount; i++) {
                zoneManager.updateZone(graph, in, friendTeam);
            }

            if (graph.friendBase == null) {
                List<Zone> friendPodsZones = ZoneUtils.findByFriendPods(graph);
                graph.friendBase = friendPodsZones.get(0);
            }
            if (graph.pathToEnemyBase == null) {
                SearchEnemyBase searchEnemyBase = new SearchEnemyBase();
                Optional<Path> pathToEnemyBase = searchEnemyBase.bfsSearch(graph, graph.friendBase);
                pathToEnemyBase.ifPresent(path -> graph.pathToEnemyBase = path);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // first line for movement commands, second line no longer used (see the protocol in the statement for details)
            System.out.println(computeMoves(graph));
            System.out.println("WAIT");
        }
    }

    private static String computeMoves(Graph graph) {
        StringBuilder movesAction = new StringBuilder();
        List<Move> moves = moveStrategy.computeMoves(graph);
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            movesAction.append(move.number)
                    .append(" ")
                    .append(move.zoneSource.zoneId)
                    .append(" ")
                    .append(move.zoneTarget.zoneId);
            if (i != moves.size() - 1)
                movesAction.append(" ");
        }
        if (movesAction.toString().isEmpty())
            return "WAIT";
        return movesAction.toString();
    }
}