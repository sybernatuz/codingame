package main.java.compete.platinum_rift_episode_2;

import main.java.compete.platinum_rift_episode_2.enums.TeamEnum;
import main.java.compete.platinum_rift_episode_2.managers.ZoneManager;
import main.java.compete.platinum_rift_episode_2.objects.Graph;
import main.java.compete.platinum_rift_episode_2.objects.Move;
import main.java.compete.platinum_rift_episode_2.objects.Path;
import main.java.compete.platinum_rift_episode_2.objects.Zone;
import main.java.compete.platinum_rift_episode_2.strategies.MoveStrategy;
import main.java.compete.platinum_rift_episode_2.utils.ZoneUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                int zoneId = in.nextInt();
                zoneManager.updateZone(graph, zoneId, in, friendTeam);
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
        moves.forEach(move ->
                movesAction.append(move.number)
                .append(" ")
                .append(move.zoneSource.zoneId)
                .append(" ")
                .append(move.zoneTarget.zoneId)
        );
        return movesAction.toString();
    }
}