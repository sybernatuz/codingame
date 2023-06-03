package analyzers;

import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class SilenceAnalyzer {

    private static final SilenceAnalyzer INSTANCE = new SilenceAnalyzer();

    public static SilenceAnalyzer getInstance() {
        return INSTANCE;
    }


    public void addSilenceRangedZones(Submarine submarine) {
        submarine.possibleLocation = submarine.possibleLocation.stream()
                .flatMap(coordinate -> withSilenceRange(coordinate).stream())
                .collect(Collectors.toList());
        submarine.coordinate = null;
    }

    private List<PossibleLocation> withSilenceRange(PossibleLocation coordinate) {
        List<PossibleLocation> withSilenceRange = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            PossibleLocation last = coordinate;
            for (int i = 1; i <= 4; i++) {
                PossibleLocation location = new PossibleLocation(direction.toCoordinate(last));
                location.historic = new ArrayList<>(last.historic);
                location.historic.add(last);
                if (!location.isValid() || location.alreadyVisited()) {
                    break;
                }
                last = location;
                withSilenceRange.add(location);
            }
        }
        withSilenceRange.add(coordinate);
        return withSilenceRange;
    }

}
