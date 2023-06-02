import objects.Action;
import objects.Graph;
import inmemory.InMemory;
import objects.Zone;
import objects.ZoneType;
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

    problem eggs place 4206422443215320600

    if total zone focus < unit number then focus closest eggs seed=-4848616068502044000


    bug -8590989990640396000          seed=3074541236684371500 seed=-5556325977528636000

    bug  seed=-3734732324122437600

    problem seed=5819134781606688000        seed=7490719657249462000



    seed=6987479048211140000


    seed=4490416902510905300


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
                .forEach(zone -> {
                    zone.type = ZoneType.MY_BASE;
                    graph.myBases.add(zone);
                });
        IntStream.range(0, numberOfBases)
                .mapToObj(i -> graph.zones.get(in.nextInt()))
                .forEach(zone -> {
                    zone.type = ZoneType.ENEMY_BASE;
                    graph.enemyBases.add(zone);
                });

        InMemory inMemory = new InMemory(graph);

        LogsUtils.log("Total crystals : %s", inMemory.totalCrystals);
        LogsUtils.log("Total eggs : %s", inMemory.totalEggs);
        LogsUtils.log("Total zones : %s", graph.zones.values().size());

        while (true) {
            inMemory.turn++;

            int myScore = in.nextInt();
            int oppScore = in.nextInt();
            IntStream.range(0, numberOfCells)
                    .forEach(index -> cells.get(index).update(in));

            inMemory.update(graph);

            List<Action> actions = Beans.strategiesBridge.computeActions(graph, inMemory);

            StringJoiner stringJoiner = new StringJoiner(";");
            actions.forEach(action -> stringJoiner.add(action.toString()));
            if (stringJoiner.toString().isEmpty()) {
                stringJoiner.add("WAIT");
            }
            System.out.println(stringJoiner);
            // WAIT | LINE <sourceIdx> <targetIdx> <strength> | BEACON <cellIdx> <strength> | MESSAGE <text>
        }
    }
}