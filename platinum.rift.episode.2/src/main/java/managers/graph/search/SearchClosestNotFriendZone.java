package managers.graph.search;

import enums.TeamEnum;
import objects.Graph;
import objects.Zone;

public class SearchClosestNotFriendZone extends AbstractBfsSearch {

    @Override
    protected boolean isFound(Graph graph, Zone current) {
        return !current.team.equals(TeamEnum.FRIEND);
    }
}
