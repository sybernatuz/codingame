import beans.*;
import strategies.StrategyFactory;

import java.util.Optional;
import java.util.Scanner;


public class Player {

    private final Scanner in = new Scanner(System.in);
    private final StrategyFactory strategyFactory = new StrategyFactory();

    public static void main(String[] args) {
        new Player().run();
    }

    void run() {
        // Parse initial conditions
        Board board = new Board(in);

        while (true) {
            // Parse current state of the game
            board.update(in);

            // Insert your strategy here
            for (Entity robot : board.myTeam.robots) {
                robot.action = strategyFactory.findStrategy(board, robot)
                        .computeAction(board, robot)
                        .orElse(Action.none());
            }

            // Send your actions for this turn
            board.myTeam.robots.stream()
                    .map(entity -> entity.action)
                    .forEach(System.out::println);
        }
    }
}