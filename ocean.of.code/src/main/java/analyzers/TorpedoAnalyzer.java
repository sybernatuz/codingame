package analyzers;

import objects.Submarine;
import objects.actions.Action;

import java.util.stream.Collectors;

class TorpedoAnalyzer {

    private static final TorpedoAnalyzer INSTANCE = new TorpedoAnalyzer();

    public static TorpedoAnalyzer getInstance() {
        return INSTANCE;
    }

    public void filterByTorpedo(Submarine submarine, Action torpedo) {
        submarine.possibleLocation = submarine.possibleLocation.stream()
                .filter(coordinate -> coordinate.computeDistance(torpedo.coordinate) <= 4)
                .collect(Collectors.toList());
    }
}
