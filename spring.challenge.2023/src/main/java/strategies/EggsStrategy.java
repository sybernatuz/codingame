package strategies;

import objects.Action;
import objects.Cell;
import objects.Graph;
import search.BfsSearch;
import search.SearchClosestEgg;

import java.util.Optional;

public class EggsStrategy {

    private final BfsSearch bfsSearch = new SearchClosestEgg();

    public Optional<Action> goToClosestEgg(Graph graph) {
        Cell myBase = graph.cells.get(graph.myBases.get(0));
        Optional<Cell> closestEgg =  bfsSearch.search(graph, myBase);

        return closestEgg.map(cell -> {
            Action action = new Action(Action.Type.LINE);
            action.strength = 2;
            action.index1 = myBase.index;
            action.index2 = cell.index;
            return action;
        });
    }
}
