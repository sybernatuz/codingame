package managers;

import enums.OwnerEnum;
import initializers.FactoryInitializer;
import objects.Bomb;
import objects.Factory;
import objects.Troop;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EntityManagerTest {

    private static EntityManager entityManager;

    private List<Bomb> bombs;
    private List<Troop> troops;
    private List<Factory> factories;

    @BeforeClass
    public static void initEntityManager() {
        entityManager = new EntityManager();
    }

    @Before
    public void initLists() {
        bombs = new ArrayList<>();
        troops = new ArrayList<>();
        factories = FactoryInitializer.initFactories();
    }

    @Test
    public void updateEntityAddBombTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_add_bomb.txt");
        Scanner scanner = new Scanner(is);

        entityManager.updateEntity(scanner, troops, factories, bombs);
        Assert.assertEquals(1, bombs.size());
        Assert.assertEquals(1, bombs.get(0).id);
    }

    @Test
    public void updateEntityAddTroopTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_add_troop.txt");
        Scanner scanner = new Scanner(is);

        entityManager.updateEntity(scanner, troops, factories, bombs);
        Assert.assertEquals(1, troops.size());
        Assert.assertEquals(1, troops.get(0).id);
    }

    @Test
    public void updateEntityRootFactoryTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_factory.txt");
        Scanner scanner = new Scanner(is);

        entityManager.updateEntity(scanner, troops, factories, bombs);
        Assert.assertEquals(4, factories.size());
        Factory factoryId1 = FactoryInitializer.findFactoryById(1, factories);
        Assert.assertEquals(1, factoryId1.id);
        Assert.assertEquals(OwnerEnum.ENEMY, factoryId1.owner);
    }

    @Test
    public void updateEntityChildFactoriesTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/update_entity_factory.txt");
        Scanner scanner = new Scanner(is);

        entityManager.updateEntity(scanner, troops, factories, bombs);
        factories.stream()
                .flatMap(factory -> factory.neighbours.stream())
                .map(link -> link.neighbour)
                .filter(factory -> factory.id == 1)
                .forEach(factory -> Assert.assertEquals(OwnerEnum.ENEMY, factory.owner));
    }

    @Test
    public void addLinkTest() {
        InputStream is = getClass().getResourceAsStream("/managers/entitymanager/add_link.txt");
        Scanner scanner = new Scanner(is);

        entityManager.addLink(scanner, factories);
        Assert.assertEquals(4, factories.size());
        Factory factoryId2 = FactoryInitializer.findFactoryById(2, factories);
        Factory factoryId3 = FactoryInitializer.findFactoryById(3, factories);
        List<Factory> neighboursFactoryId2 = getNeighbours(factoryId2);
        List<Factory> neighboursFactoryId3 = getNeighbours(factoryId3);
        Assert.assertTrue(neighboursFactoryId2.contains(factoryId3));
        Assert.assertTrue(neighboursFactoryId3.contains(factoryId2));

    }

    private List<Factory> getNeighbours(Factory factory) {
        return factory.neighbours.stream()
                .map(link -> link.neighbour)
                .collect(Collectors.toList());
    }
}
