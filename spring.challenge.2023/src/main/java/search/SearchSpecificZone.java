package search;

import objects.Graph;
import objects.Zone;

public class SearchSpecificZone extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current, Zone target) {
        return current.equals(target);
    }
}
