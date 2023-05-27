import objects.Action;
import objects.Graph;
import inmemory.InMemory;
import objects.Zone;
import singleton.Beans;
import utils.LogsUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Player {

    /*

    don't go to resources far 4670517391607826000
     */

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int numberOfCells = in.nextInt(); // amount of hexagonal cells in this map
        Map<Integer, Zone> cells = IntStream.range(0, numberOfCells)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        index -> new Zone(in, index)
                ));

        Graph graph = new Graph(cells);

        int numberOfBases = in.nextInt();
        IntStream.range(0, numberOfBases)
                .mapToObj(i -> graph.zones.get(in.nextInt()))
                .forEach(zone -> graph.myBases.add(zone));
        IntStream.range(0, numberOfBases)
                .mapToObj(i -> graph.zones.get(in.nextInt()))
                .forEach(zone -> graph.enemyBases.add(zone));

        InMemory inMemory = new InMemory(graph);

        // game loop
        while (true) {
            LogsUtils.log("Total crystal : %s", inMemory.totalCrystals);

            IntStream.range(0, numberOfCells)
                    .forEach(index -> cells.get(index).update(in));

            List<Action> actions = Beans.strategiesBridge.computeActions(graph, inMemory);

            StringJoiner stringJoiner = new StringJoiner(";");
            actions.forEach(action -> stringJoiner.add(action.toString()));
            System.out.println(stringJoiner);
            inMemory.turn++;
            // WAIT | LINE <sourceIdx> <targetIdx> <strength> | BEACON <cellIdx> <strength> | MESSAGE <text>
        }
    }
}