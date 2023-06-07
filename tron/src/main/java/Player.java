import objects.*;
import path.MaxEmptyPathFinder;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        // game loop
        while (true) {
            Game.getInstance().update(in);
            Direction direction = findSafePosition().orElse(Direction.UP);
            System.out.println(direction); // A single line with UP, DOWN, LEFT or RIGHT
        }
    }

    static Optional<Direction> findSafePosition() {
        Coordinate myCoordinate = Game.getInstance().getMyMoto().coordinate;

        List<Coordinate> path = MaxEmptyPathFinder.getInstance().findMaxEmptyPath(myCoordinate);
        System.err.println(path.size());
        if (path.isEmpty()) {
            return Optional.empty();
        }

        Coordinate nextMove = path.get(0);
        Direction direction = Direction.fromPosition(myCoordinate, nextMove);
        return Optional.ofNullable(direction);
    }
}

