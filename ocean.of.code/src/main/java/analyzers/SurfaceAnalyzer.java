package analyzers;

import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;

import java.util.Optional;
import java.util.stream.Collectors;

class SurfaceAnalyzer {

    private static final SurfaceAnalyzer INSTANCE = new SurfaceAnalyzer();

    public static SurfaceAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterBySector(Submarine submarine, Action surface) {
        int surfaceSector = Optional.of(surface)
                .map(action -> action.sector)
                .orElseGet(() -> submarine.coordinateFinal.computeSector());
        submarine.possibleLocation = submarine.possibleLocation.stream()
                .filter(coordinate -> coordinate.computeSector() == surfaceSector)
                .map(possibleLocation -> new PossibleLocation(possibleLocation.x, possibleLocation.y))
                .collect(Collectors.toList());
    }
}
