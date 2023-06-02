package analyzers;

import objects.PossibleLocation;
import objects.Submarine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SilenceAnalyzer {

    private static final SilenceAnalyzer INSTANCE = new SilenceAnalyzer();

    public static SilenceAnalyzer getInstance() {
        return INSTANCE;
    }

    public void addSilenceRangedZones(Submarine submarine) {
        List<PossibleLocation> zoneInRange = submarine.possibleLocation.stream()
                .flatMap(coordinate -> withSilenceRange(coordinate).stream())
                .collect(Collectors.toList());
        submarine.possibleLocation = clearDuplicate(zoneInRange);
        submarine.coordinate = null;
    }

    private List<PossibleLocation> clearDuplicate(List<PossibleLocation> locations) {
        locations.stream()
                .filter(possibleLocation -> locations.stream().filter(p -> p.equals(possibleLocation)).count() > 1)
                .forEach(possibleLocation -> possibleLocation.historic.clear());
        return locations.stream()
                .distinct()
                .collect(Collectors.toList());
    }


    private List<PossibleLocation> withSilenceRange(PossibleLocation coordinate) {
        List<PossibleLocation> withSilenceRange = new ArrayList<>();
        int[][] moves = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        for (int[] move : moves) {
            for (int i = 1; i <= 4; i++) {
                PossibleLocation location = new PossibleLocation(coordinate);
                location.x += i * move[0];
                location.y += i * move[1];
                location.historic.add(coordinate);
                if (!location.isValid() || location.alreadyVisited()) {
                    break;
                }
                withSilenceRange.add(location);
            }
        }
        withSilenceRange.add(coordinate);
        return withSilenceRange;
    }
}
