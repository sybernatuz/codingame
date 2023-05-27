package search;

import objects.Zone;
import objects.ZoneType;
import objects.Graph;

public class SearchClosestEgg extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current, Zone target) {
        return current.type.equals(ZoneType.EGG) && current.resources > 0;
    }
}
