package objects;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PossibleLocation extends Coordinate {

    public Set<Historic> histories = new HashSet<>();

    public PossibleLocation(int x, int y) {
        super(x, y);
        histories.add(new Historic());
    }

    public PossibleLocation(Coordinate coordinate) {
        super(coordinate.x, coordinate.y);
    }

    public boolean alreadyVisited() {
        histories = histories.parallelStream()
                    .filter(historic -> !historic.coordinates.contains(this))
                    .collect(Collectors.toSet());
        return histories.isEmpty();
    }

    public void copyHistories(Set<Historic> toCopy) {
        histories = toCopy.parallelStream()
                .map(historic -> new Historic(new HashSet<>(historic.coordinates)))
                .collect(Collectors.toSet());
    }

    public void addToHistoric(PossibleLocation location) {
        Coordinate coordinate = location.toCoordinate();
        histories.forEach(h -> h.coordinates.add(coordinate));
    }

    public Coordinate toCoordinate() {
        return new Coordinate(x, y);
    }
}