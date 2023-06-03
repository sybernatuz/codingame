package analyzers;

import objects.Coordinate;
import objects.Grid;
import objects.PossibleLocation;
import objects.Submarine;
import org.junit.Test;

public class SilenceAnalyzerTest {

    @Test
    public void addSilenceRangedZones() {

        Grid.getInstance().width = 15;
        Grid.getInstance().height = 15;

        Grid.getInstance().blocked.add(new Coordinate(1, 3));

        PossibleLocation location = new PossibleLocation(0, 3);
        location.historic.add(new PossibleLocation(0, 2));
        location.historic.add(new PossibleLocation(0, 1));
        location.historic.add(new PossibleLocation(0, 0));

        Submarine submarine = new Submarine();
        submarine.possibleLocation.add(location);

        SilenceAnalyzer.getInstance().addSilenceRangedZones(submarine);

        assert submarine.possibleLocation.contains(new PossibleLocation(0, 3));
        assert submarine.possibleLocation.contains(new PossibleLocation(0, 4));
        assert submarine.possibleLocation.contains(new PossibleLocation(0, 5));
        assert submarine.possibleLocation.contains(new PossibleLocation(0, 6));
        assert submarine.possibleLocation.contains(new PossibleLocation(0, 7));
    }

}