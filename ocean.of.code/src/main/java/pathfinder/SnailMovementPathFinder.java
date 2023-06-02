package pathfinder;

import objects.Coordinate;
import objects.actions.Direction;

import java.util.ArrayList;
import java.util.List;

class SnailMovementPathFinder {

    private static final SnailMovementPathFinder INSTANCE = new SnailMovementPathFinder();

    public static SnailMovementPathFinder getInstance() {
        return INSTANCE;
    }


    public List<Coordinate> snailPath(List<Coordinate> square) {
        Size size = new Size(square);
        List<Coordinate> path = new ArrayList<>();
        Coordinate snailPosition = middleSquare(size);
        path.add(snailPosition);
        Direction direction = Direction.E;
        int stepCount = 1;

        while (isValidPosition(snailPosition, size)) {
            for (int i = 0; i < stepCount / 2; i++) {
                Coordinate nextPosition = nextPosition(direction, snailPosition);
                snailPosition = nextPosition;
                if (!isValidPosition(snailPosition, size))
                    break;
                path.add(nextPosition);
            }
            stepCount++;
            direction = nextDirection(direction);
        }

        return path;
    }

    private boolean isValidPosition(Coordinate coordinate, Size size) {
        return coordinate.x > size.minX && coordinate.x < size.maxX && coordinate.y > size.minY && coordinate.y < size.maxY;
    }

    private Coordinate nextPosition(Direction direction, Coordinate snailPosition) {
        switch (direction) {
            case E:
                return new Coordinate(snailPosition.x, snailPosition.y + 1);
            case S:
                return new Coordinate(snailPosition.x + 1, snailPosition.y);
            case W:
                return new Coordinate(snailPosition.x, snailPosition.y - 1);
            case N:
                return new Coordinate(snailPosition.x - 1, snailPosition.y);
        }
        throw new RuntimeException();
    }

    private Direction nextDirection(Direction direction) {
        switch (direction) {
            case E:
                return Direction.S;
            case S:
                return Direction.W;
            case W:
                return Direction.N;
            case N:
                return Direction.E;
        }
        throw new RuntimeException();
    }

    private Coordinate middleSquare(Size size) {
        int centerX = (size.minX + size.maxX) / 2;
        int centerY = (size.minY + size.maxY) / 2;
        return new Coordinate(centerX, centerY);
    }

    static class Size {
        int minX;
        int maxX;
        int minY;
        int maxY;

        public Size(List<Coordinate> square) {
            minX = square.stream()
                .mapToInt(coordinate -> coordinate.x)
                .min()
                .orElseThrow(RuntimeException::new);
            maxX = square.stream()
                .mapToInt(coordinate -> coordinate.x)
                .max()
                .orElseThrow(RuntimeException::new);
            minY = square.stream()
                .mapToInt(coordinate -> coordinate.y)
                .min()
                .orElseThrow(RuntimeException::new);
            maxY = square.stream()
                    .mapToInt(coordinate -> coordinate.y)
                    .max()
                    .orElseThrow(RuntimeException::new);
        }
    }
}
