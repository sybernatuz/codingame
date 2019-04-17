package managers.graph.search;

import enums.TeamEnum;
import objects.Graph;
import objects.Zone;

public class SearchClosestPlatinumSource extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current) {
        return current.platinum > 0 && !current.team.equals(TeamEnum.FRIEND);
    }
}
