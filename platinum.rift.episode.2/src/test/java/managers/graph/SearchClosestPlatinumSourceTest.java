package managers.graph;

import initializers.GraphInitializer;
import initializers.ZoneInitializer;
import objects.Graph;
import objects.Path;
import objects.Zone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


public class SearchClosestPlatinumSourceTest {

    private BfsSearch bfsSearch;
    private Graph                       graph;

    @Before
    public void init() {
        bfsSearch = new BfsSearch();
        graph = GraphInitializer.initGraph();
    }

    @Test
    public void bfsSearchTest() {
        Zone zoneSource = ZoneInitializer.initFriendZone3();
        Optional<Path> pathToNotFriendPlatinum = bfsSearch.search(graph, zoneSource);
        Assert.assertTrue(pathToNotFriendPlatinum.isPresent());
        Assert.assertEquals(6, pathToNotFriendPlatinum.get().zones.get(0).id);
        Assert.assertEquals(3, pathToNotFriendPlatinum.get().zones.get(1).id);
        Assert.assertEquals(1, pathToNotFriendPlatinum.get().zones.get(2).id);
        Assert.assertEquals(2, pathToNotFriendPlatinum.get().zones.get(3).id);
        Assert.assertEquals(4, pathToNotFriendPlatinum.get().zones.get(4).id);
    }
}
