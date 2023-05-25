import objects.Action;
import objects.Cell;
import objects.Graph;
import strategies.EggsStrategy;
import strategies.FoodStrategy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private final static EggsStrategy eggsStrategy = new EggsStrategy();
    private final static FoodStrategy foodStrategy = new FoodStrategy();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int numberOfCells = in.nextInt(); // amount of hexagonal cells in this map
        Map<Integer, Cell> cells = IntStream.range(0, numberOfCells)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        index -> new Cell(in, index)
                ));

        Graph graph = new Graph(cells);

        int numberOfBases = in.nextInt();
        IntStream.range(0, numberOfBases)
                .forEach(integer -> graph.myBases.add(in.nextInt()));
        IntStream.range(0, numberOfBases)
                .forEach(integer -> graph.enemyBases.add(in.nextInt()));


        int turn = 0;
        // game loop
        while (true) {
            IntStream.range(0, numberOfCells)
                    .forEach(index -> cells.get(index).update(in));

            List<Action> actions = new ArrayList<>();
            eggsStrategy.goToClosestEgg(graph)
                    .ifPresent(actions::add);
            foodStrategy.goToClosestFood(graph)
                    .ifPresent(actions::add);

            StringJoiner stringJoiner = new StringJoiner(";");
            actions.forEach(action -> stringJoiner.add(action.toString()));
            System.out.println(stringJoiner);
            turn++;
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // WAIT | LINE <sourceIdx> <targetIdx> <strength> | BEACON <cellIdx> <strength> | MESSAGE <text>
        }
    }
}