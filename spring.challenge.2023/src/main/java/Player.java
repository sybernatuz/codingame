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

    don't go to resources far 4670517391607826000

    7579259595182802000

    add better strength to contested nodes -1596783504159507200            2013886087322872000

    prioritize farest food from enemy base seed=-1767202576435098400

    problem eggs place 4206422443215320600

    compute nodes to go (all closest to my base than enemy base)   seed=-6204419596454118000            1103460450409936100          seed=-8588803305787791000

    focus eggs prio -2183887228794573600

    remove useless node for path   -6650087268975064000


    bug -8590989990640396000

    bug  seed=-3734732324122437600

    problem seed=5819134781606688000


    timeout 5106072160139232000          seed=1641917334415600400
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
        LogsUtils.log("Total zones : %s", graph.zones.values().size());

        while (true) {
            inMemory.turn++;

            IntStream.range(0, numberOfCells)
                    .forEach(index -> cells.get(index).update(in));

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