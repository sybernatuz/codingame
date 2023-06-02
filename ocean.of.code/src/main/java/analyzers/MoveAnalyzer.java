package analyzers;

import objects.Coordinate;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.Collections;
import java.util.stream.Collectors;

class MoveAnalyzer {

    private static final MoveAnalyzer INSTANCE = new MoveAnalyzer();

    public static MoveAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterByMove(Submarine submarine, Action move) {
        if (submarine.coordinate != null) {
            submarine.coordinate = computeNewCoordinate(submarine.coordinate, move);
            submarine.possibleLocation = Collections.singletonList(new PossibleLocation(submarine.coordinate));
        } else {
            tryToFindLocation(submarine, move);
        }
    }

    private PossibleLocation computeNewCoordinate(PossibleLocation possibleLocation, Action move) {
        PossibleLocation newCoordinate = new PossibleLocation(possibleLocation);
        newCoordinate.historic.add(possibleLocation);
        switch (move.direction) {
            case E:
                newCoordinate.x++;
                break;
            case W:
                newCoordinate.x--;
                break;
            case N:
                newCoordinate.y--;
                break;
            case S:
                newCoordinate.y++;
                break;
        }
        return newCoordinate;
    }

    private void tryToFindLocation(Submarine submarine, Action move) {
        submarine.possibleLocation = submarine.possibleLocation.stream()
                .map(coordinate -> computeNewCoordinate(coordinate, move))
                .filter(Coordinate::isValid)
                .filter(possibleLocation -> !possibleLocation.alreadyVisited())
                .collect(Collectors.toList());
    }
}
