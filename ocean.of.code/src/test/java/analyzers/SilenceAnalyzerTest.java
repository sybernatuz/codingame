package analyzers;

import objects.Coordinate;
import objects.Grid;
import objects.PossibleLocation;
import objects.Submarine;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SilenceAnalyzerTest {

    @Test
    public void test() {

        Grid.getInstance().width = 15;
        Grid.getInstance().height = 15;

        Grid.getInstance().blocked.add(new Coordinate(1, 3));

        PossibleLocation location = new PossibleLocation(0, 3);
        location.addToHistoric(new PossibleLocation(0, 2));
        location.addToHistoric(new PossibleLocation(0, 1));
        location.addToHistoric(new PossibleLocation(0, 0));

        Submarine submarine = new Submarine();
        submarine.possibleLocation.add(location);

        SilenceAnalyzer.getInstance().addSilenceRangedZones(submarine);

        Assertions.assertThat(submarine.possibleLocation)
                .contains(
                        new PossibleLocation(0, 3),
                        new PossibleLocation(0, 4),
                        new PossibleLocation(0, 5),
                        new PossibleLocation(0, 6),
                        new PossibleLocation(0, 7)
                );
    }

}