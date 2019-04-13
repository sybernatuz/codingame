package initializers;

import enums.OwnerEnum;
import objects.Factory;
import objects.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FactoryInitializer {

    /*
            Factory 3 ----4---- Factory 1 ----2---- Factory 2
                                    |
                                    |
                                    3
                                    |
                                    |
                                Factory 4
     */

    public static Factory findFactoryById(int id, List<Factory> factories) {
        return factories.stream()
                .filter(factory -> factory.id == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Factory not found"));
    }

    public static List<Factory> initFactories() {
        return Arrays.asList(
                initFriendFactory1(true),
                initFriendFactory2(true),
                initEnemyFactory1(true),
                initEnemyFactory2(true)
        );
    }

    public static Factory initFriendFactory1(boolean initNeighbours) {
        Factory factory = new Factory();
        factory.id = 1;
        factory.troopNumber = 5;
        factory.owner = OwnerEnum.FRIEND;
        factory.production = 3;
        if (initNeighbours)
            factory.neighbours = initAllyFactory1Neighbours();
        return factory;
    }

    public static Factory initFriendFactory2(boolean initNeighbours) {
        Factory factory = new Factory();
        factory.id = 2;
        factory.troopNumber = 80;
        factory.owner = OwnerEnum.FRIEND;
        factory.production = 2;
        if (initNeighbours)
            factory.neighbours = initAllyFactory2Neighbours();
        return factory;
    }

    public static Factory initEnemyFactory1(boolean initNeighbours) {
        Factory factory = new Factory();
        factory.id = 3;
        factory.troopNumber = 70;
        factory.owner = OwnerEnum.ENEMY;
        factory.production = 2;
        if (initNeighbours)
            factory.neighbours = initEnemyFactory1Neighbours();
        return factory;
    }

    public static Factory initEnemyFactory2(boolean initNeighbours) {
        Factory factory = new Factory();
        factory.id = 4;
        factory.troopNumber = 110;
        factory.owner = OwnerEnum.ENEMY;
        factory.production = 3;
        if (initNeighbours)
            factory.neighbours = initEnemyFactory2Neighbours();
        return factory;
    }

    public static List<Link> initAllyFactory1Neighbours() {
        Link link1 = new Link();
        link1.distance = 2;
        link1.neighbour = initFriendFactory2(false);
        Link link2 = new Link();
        link2.distance = 4;
        link2.neighbour = initEnemyFactory1(false);
        Link link3 = new Link();
        link3.distance = 3;
        link3.neighbour = initEnemyFactory2(false);
        return new ArrayList<>(Arrays.asList(
                link1,
                link2,
                link3
        ));
    }

    public static List<Link> initAllyFactory2Neighbours() {
        Link link1 = new Link();
        link1.distance = 2;
        link1.neighbour = initFriendFactory1(false);
        return new ArrayList<>(Collections.singletonList(link1));
    }

    public static List<Link> initEnemyFactory1Neighbours() {
        Link link1 = new Link();
        link1.distance = 1;
        link1.neighbour = initFriendFactory1(false);
        return new ArrayList<>(Collections.singletonList(link1));
    }

    public static List<Link> initEnemyFactory2Neighbours() {
        Link link1 = new Link();
        link1.distance = 3;
        link1.neighbour = initFriendFactory1(false);
        return new ArrayList<>(Collections.singletonList(link1));
    }


}
