package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PossibleLocation extends Coordinate {

    public List<Coordinate> historic = new ArrayList<>();

    public PossibleLocation(int x, int y) {
        super(x, y);
    }

    public PossibleLocation(Coordinate coordinate) {
        super(coordinate.x, coordinate.y);
    }

    public PossibleLocation(PossibleLocation possibleLocation) {
        super(possibleLocation.x, possibleLocation.y);
        historic = new ArrayList<>(possibleLocation.historic);
    }

    public boolean alreadyVisited() {
        return historic.contains(this);
    }


}
