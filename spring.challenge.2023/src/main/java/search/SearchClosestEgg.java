package search;

import objects.Cell;
import objects.CellType;
import objects.Graph;

public class SearchClosestEgg extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Cell current) {
        return current.type.equals(CellType.EGG) && current.resources > 0;
    }
}
