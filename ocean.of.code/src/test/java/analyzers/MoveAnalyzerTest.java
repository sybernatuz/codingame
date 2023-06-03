package analyzers;

import objects.Grid;
import objects.PossibleLocation;
import objects.Submarine;
import objects.actions.Action;
import objects.actions.Direction;
import objects.actions.Type;
import org.junit.Test;

public class MoveAnalyzerTest {

    @Test
    public void test() {
        Grid.getInstance().width = 15;
        Grid.getInstance().height = 15;

        PossibleLocation location = new PossibleLocation(0, 0);

        Submarine submarine = new Submarine();
        submarine.possibleLocation.add(location);

        Action action = new Action();
        action.type = Type.MOVE;
        action.direction = Direction.S;

        MoveAnalyzer.getInstance().filterByMove(submarine, action);

        assert submarine.possibleLocation.size() == 1;
        assert submarine.possibleLocation.get(0).equals(new PossibleLocation(0, 1));
    }

}