package strategies;

import objects.Action;
import objects.Cell;
import objects.Graph;
import search.BfsSearch;
import search.SearchClosestFood;

import java.util.Optional;

public class FoodStrategy {

    private final BfsSearch bfsSearch = new SearchClosestFood();

    public Optional<Action> goToClosestFood(Graph graph) {
        Cell myBase = graph.cells.get(graph.myBases.get(0));
        Optional<Cell> closestFood =  bfsSearch.search(graph, myBase);

        return closestFood.map(cell -> {
            Action action = new Action(Action.Type.LINE);
            action.strength = 1;
            action.index1 = myBase.index;
            action.index2 = cell.index;
            return action;
        });
    }
}
