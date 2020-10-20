import builder.ZoneBuilder;
import managers.ZoneManager;
import managers.graph.GraphManager;
import objects.Graph;
import objects.Move;
import objects.Zone;
import strategies.MoveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private static final ZoneManager zoneManager = new ZoneManager();
    private static final MoveStrategy moveStrategy = new MoveStrategy();
    private static final GraphManager graphManager = new GraphManager();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int playerCount = in.nextInt(); // the amount of players (always 2)
        int friendTeam = in.nextInt(); // my player ID (0 or 1)
        int zoneCount = in.nextInt(); // the amount of zones on the map
        int linkCount = in.nextInt(); // the amount of links between all zones
        Graph graph = new Graph();
        for (int i = 0; i < zoneCount; i++) {
            Zone zone = ZoneBuilder.init(in)
                    .withPlatinumSource(in)
                    .build();
            graph.zonesByLinkedZone.put(zone, new ArrayList<>());
        }

        for (int i = 0; i < linkCount; i++) {
            zoneManager.organizeZonesLink(in, graph);
        }

        // game loop
        while (true) {
            int myPlatinum = in.nextInt(); // your available Platinum
            IntStream.range(0, zoneCount).forEach(ignored -> zoneManager.updateZone(graph, in, friendTeam));

            graphManager.initGraphData(graph);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // first line for movement commands, second line no longer used (see the protocol in the statement for details)
            System.out.println(computeMoves(graph));
            System.out.println("WAIT");
        }
    }

    private static String computeMoves(Graph graph) {
        List<Move> moves = moveStrategy.computeMoves(graph);
        StringJoiner movesAction = new StringJoiner(" ");
        moves.forEach(move -> movesAction
                .add(String.valueOf(move.number))
                .add(String.valueOf(move.zoneSource.id))
                .add(String.valueOf(move.zoneTarget.id))
        );
        if (movesAction.toString().isEmpty())
            return "WAIT";
        return movesAction.toString();
    }
}