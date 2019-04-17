package managers.graph;

import initializers.GraphInitializer;
import initializers.ZoneInitializer;
import managers.graph.search.SearchEnemyBase;
import objects.Graph;
import objects.Path;
import objects.Zone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class SearchEnemyBaseTest {

    private SearchEnemyBase searchEnemyBase;
    private Graph           graph;

    @Before
    public void init() {
        searchEnemyBase = new SearchEnemyBase();
        graph = GraphInitializer.initGraph();
    }


    @Test
    public void bfsSearchPathFriendBaseToEnemyBaseTest() {
        Zone zoneSource = ZoneInitializer.initFriendBase();
        Optional<Path> pathToEnemyBase = searchEnemyBase.bfsSearch(graph, zoneSource);
        Assert.assertTrue(pathToEnemyBase.isPresent());
        Assert.assertEquals(3, pathToEnemyBase.get().zones.get(0).id);
        Assert.assertEquals(5, pathToEnemyBase.get().zones.get(1).id);
    }

    @Test
    public void bfsSearchPathFriendZone1ToEnemyBaseTest() {
        Zone zoneSource = ZoneInitializer.initFriendZone1();
        Optional<Path> pathToEnemyBase = searchEnemyBase.bfsSearch(graph, zoneSource);
        Assert.assertTrue(pathToEnemyBase.isPresent());
        Assert.assertEquals(1, pathToEnemyBase.get().zones.get(0).id);
        Assert.assertEquals(3, pathToEnemyBase.get().zones.get(1).id);
        Assert.assertEquals(5, pathToEnemyBase.get().zones.get(2).id);
    }
}
