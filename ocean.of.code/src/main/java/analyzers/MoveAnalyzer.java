package analyzers;

import objects.Coordinate;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.List;
import java.util.stream.Collectors;

class MoveAnalyzer {

    private static final MoveAnalyzer INSTANCE = new MoveAnalyzer();

    public static MoveAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterByMove(Submarine submarine, Action move) {
        submarine.possibleLocation = computeNewCoordinates(submarine, move);

        if (submarine.coordinate != null) {
            submarine.coordinate = computeNewCoordinate(submarine.coordinate, move);
        }
    }

    private PossibleLocation computeNewCoordinate(PossibleLocation possibleLocation, Action move) {
        PossibleLocation newCoordinate = new PossibleLocation(move.direction.toCoordinate(possibleLocation));
        newCoordinate.histories = possibleLocation.histories;
        newCoordinate.addToHistoric(possibleLocation);
        return newCoordinate;
    }

    private List<PossibleLocation> computeNewCoordinates(Submarine submarine, Action move) {
        return submarine.possibleLocation.stream()
                .map(coordinate -> computeNewCoordinate(coordinate, move))
                .filter(Coordinate::isValid)
                .filter(possibleLocation -> !possibleLocation.alreadyVisited())
                .collect(Collectors.toList());
    }
}
