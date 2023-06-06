package analyzers;

import objects.Historic;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Direction;

import java.util.*;
import java.util.stream.Collectors;

class SilenceAnalyzer {

    private static final SilenceAnalyzer INSTANCE = new SilenceAnalyzer();

    private static final int SILENCE_RANGE = 4;

    public static SilenceAnalyzer getInstance() {
        return INSTANCE;
    }


    public void addSilenceRangedZones(Submarine submarine) {
        List<PossibleLocation> possibleLocations = submarine.possibleLocation.stream()
                .flatMap(coordinate -> withSilenceRange(coordinate).stream())
                .collect(Collectors.toList());

        submarine.possibleLocation = mergeDuplicate(possibleLocations);
        submarine.coordinate = null;
    }

    private List<PossibleLocation> mergeDuplicate(List<PossibleLocation> locations) {
        Map<PossibleLocation, Set<Historic>> locationHistoriesMap = new HashMap<>();

        for (PossibleLocation location : locations) {
            locationHistoriesMap.computeIfAbsent(location, key -> new HashSet<>())
                    .addAll(location.histories);
        }

        return locationHistoriesMap.entrySet().stream()
                .peek(entry -> entry.getKey().histories = entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<PossibleLocation> withSilenceRange(PossibleLocation coordinate) {
        List<PossibleLocation> withSilenceRange = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            PossibleLocation last = coordinate;
            for (int i = 1; i <= SILENCE_RANGE; i++) {
                PossibleLocation location = new PossibleLocation(direction.toCoordinate(last));
                if (!location.isValid())
                    break;

                location.copyHistories(last.histories);
                if (location.alreadyVisited())
                    break;
                location.addToHistoric(last);
                last = location;
                withSilenceRange.add(location);
            }
        }
        withSilenceRange.add(coordinate);
        return withSilenceRange;
    }
}
