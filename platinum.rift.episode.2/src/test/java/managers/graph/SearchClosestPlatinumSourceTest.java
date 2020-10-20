package managers.graph;

import initializers.GraphInitializer;
import initializers.ZoneInitializer;
import managers.graph.search.SearchClosestPlatinumSource;
import objects.Graph;
import objects.Path;
import objects.Zone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


public class SearchClosestPlatinumSourceTest {

    private SearchClosestPlatinumSource searchClosestPlatinumSource;
    private Graph                       graph;

    @Before
    public void init() {
        searchClosestPlatinumSource = new SearchClosestPlatinumSource();
        graph = GraphInitializer.initGraph();
    }

    @Test
    public void bfsSearchTest() {
        Zone zoneSource = ZoneInitializer.initFriendZone3();
        Optional<Path> pathToNotFirendPlatinum = searchClosestPlatinumSource.search(graph, zoneSource);
        Assert.assertTrue(pathToNotFirendPlatinum.isPresent());
        Assert.assertEquals(6, pathToNotFirendPlatinum.get().zones.get(0).id);
        Assert.assertEquals(3, pathToNotFirendPlatinum.get().zones.get(1).id);
        Assert.assertEquals(1, pathToNotFirendPlatinum.get().zones.get(2).id);
        Assert.assertEquals(2, pathToNotFirendPlatinum.get().zones.get(3).id);
        Assert.assertEquals(4, pathToNotFirendPlatinum.get().zones.get(4).id);
    }
}
