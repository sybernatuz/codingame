package utils;

import initializers.FactoryInitializer;
import objects.Factory;
import objects.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GraphUtilsTest {

    private List<Factory> factories;

    @Before
    public void initLists() {
        factories = FactoryInitializer.initFactories();
    }

    @Test
    public void findPathToFriendFactoryInDangerTest() {
        Factory safeFactory = FactoryInitializer.findFactoryById(2, factories);
        Factory factoryInDanger = FactoryInitializer.findFactoryById(1, factories);
        Factory enemyFactory = FactoryInitializer.findFactoryById(3, factories);
        Path path = GraphUtils.findPathToFriendFactoryInDanger(safeFactory, factories);
        Assert.assertNotNull(path);
        Assert.assertEquals(path.factories.get(0), factoryInDanger);
        Assert.assertEquals(path.factories.get(1), enemyFactory);
    }
}
