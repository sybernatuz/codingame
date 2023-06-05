package analyzers;

import objects.Grid;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MoveAnalyzerTest {

    @Test
    public void test() {
        Grid.getInstance().width = 15;
        Grid.getInstance().height = 15;


        PossibleLocation location0 = new PossibleLocation(0, 0);
        PossibleLocation location1 = new PossibleLocation(0, 1);
        location1.addToHistoric(location0);

        Submarine submarine = new Submarine();
        submarine.possibleLocation.add(location1);

        Action action = new Action();
        action.type = Type.MOVE;
        action.direction = Direction.S;

        MoveAnalyzer.getInstance().filterByMove(submarine, action);

        Assertions.assertThat(submarine.possibleLocation)
                .hasSize(1)
                .anySatisfy(possibleLocation -> {
                    Assertions.assertThat(possibleLocation).isEqualTo(new PossibleLocation(0, 2));
                    Assertions.assertThat(possibleLocation.histories)
                            .hasSize(1)
                            .flatMap(historic -> historic.coordinates)
                            .containsExactly(location0, location1);
                });
    }

}