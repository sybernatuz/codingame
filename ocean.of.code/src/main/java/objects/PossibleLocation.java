package objects;

import java.util.ArrayList;
import java.util.List;

public class PossibleLocation extends Coordinate {

    public List<PossibleLocation> historic = new ArrayList<>();

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
