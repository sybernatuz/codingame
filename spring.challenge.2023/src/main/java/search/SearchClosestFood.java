package search;

import objects.Cell;
import objects.CellType;
import objects.Graph;

public class SearchClosestFood extends AbstractBfsSearch{
    @Override
    protected boolean isFound(Graph graph, Cell current) {
        return current.resources > 0 && current.type.equals(CellType.FOOD);
    }
}
