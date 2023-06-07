package utils;

import initializers.FactoryInitializer;
import objects.Factory;
import objects.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GraphUtilsTest {

    @Before
    public void initLists() {
        FactoryInitializer.initFactories();
    }

    @Test
    public void findPathToFriendFactoryInDangerTest() {
        Factory safeFactory = FactoryInitializer.findFactoryById(2);
        Factory factoryInDanger = FactoryInitializer.findFactoryById(1);
        Factory enemyFactory = FactoryInitializer.findFactoryById(3);
        Path path = GraphUtils.findPathToFriendFactoryInDanger(safeFactory);
        Assert.assertNotNull(path);
        Assert.assertEquals(path.factories.get(0), factoryInDanger);
        Assert.assertEquals(path.factories.get(1), enemyFactory);
    }
}
