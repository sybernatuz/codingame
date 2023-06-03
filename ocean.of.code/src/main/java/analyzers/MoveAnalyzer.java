package analyzers;

import objects.Coordinate;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.ArrayList;
import java.util.stream.Collectors;

class MoveAnalyzer {

    private static final MoveAnalyzer INSTANCE = new MoveAnalyzer();

    public static MoveAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterByMove(Submarine submarine, Action move) {
        computeNewCoordinates(submarine, move);

        if (submarine.coordinate != null)
            submarine.coordinate = computeNewCoordinate(submarine.coordinate, move);
    }

    private PossibleLocation computeNewCoordinate(PossibleLocation possibleLocation, Action move) {
        PossibleLocation newCoordinate = new PossibleLocation(move.direction.toCoordinate(possibleLocation));
        newCoordinate.historic = new ArrayList<>(possibleLocation.historic);
        possibleLocation.historic.clear();
        newCoordinate.historic.add(possibleLocation);
        return newCoordinate;
    }

    private void computeNewCoordinates(Submarine submarine, Action move) {
        submarine.possibleLocation = submarine.possibleLocation.stream()
                .map(coordinate -> computeNewCoordinate(coordinate, move))
                .filter(Coordinate::isValid)
                .filter(possibleLocation -> !possibleLocation.alreadyVisited())
                .collect(Collectors.toList());
    }
}
