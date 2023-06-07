package initializers;

import enums.OwnerEnum;
import game.Game;
import objects.Factory;
import objects.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryInitializer {

    /*
            Factory 3 ----4---- Factory 1 ----2---- Factory 2
              FRIEND   \            |            /   ENEMY
                        \           |           /
                         2          3          2
                          \         |         /
                           \        |        /
                                Factory 4
     */

    public static Factory findFactoryById(int id) {
        return Game.getInstance().factories.stream()
                .filter(factory -> factory.id == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Factory not found"));
    }

    public static void initFactories() {
        Factory factory1 = iniFactory1();
        Factory factory2 = initFactory2();
        Factory factory3 = initFactory3();
        Factory factory4 = initFactory4();
        factory1.neighbours = initFactory1Neighbours(factory2, factory3, factory4);
        factory2.neighbours = initFactory2Neighbours(factory1, factory4);
        factory3.neighbours = initFactory3Neighbours(factory1, factory4);
        factory4.neighbours = initFactory4Neighbours(factory1, factory2, factory3);

        Game.getInstance().factories.addAll(Arrays.asList(
                factory1,
                factory2,
                factory3,
                factory4
        ));
    }

    public static Factory iniFactory1() {
        Factory factory = new Factory();
        factory.id = 1;
        factory.troopNumber = 5;
        factory.owner = OwnerEnum.NONE;
        factory.production = 3;
        return factory;
    }

    public static Factory initFactory2() {
        Factory factory = new Factory();
        factory.id = 2;
        factory.troopNumber = 80;
        factory.owner = OwnerEnum.ENEMY;
        factory.production = 2;
        return factory;
    }

    public static Factory initFactory3() {
        Factory factory = new Factory();
        factory.id = 3;
        factory.troopNumber = 70;
        factory.owner = OwnerEnum.FRIEND;
        factory.production = 2;
        return factory;
    }

    public static Factory initFactory4() {
        Factory factory = new Factory();
        factory.id = 4;
        factory.troopNumber = 110;
        factory.owner = OwnerEnum.NONE;
        factory.production = 3;
        return factory;
    }

    public static List<Link> initFactory1Neighbours(Factory factory2, Factory factory3, Factory factory4) {
        Link link1 = new Link();
        link1.distance = 2;
        link1.neighbour = factory2;
        Link link2 = new Link();
        link2.distance = 4;
        link2.neighbour = factory3;
        Link link3 = new Link();
        link3.distance = 3;
        link3.neighbour = factory4;
        return new ArrayList<>(Arrays.asList(
                link1,
                link2,
                link3
        ));
    }

    public static List<Link> initFactory2Neighbours(Factory factory1, Factory factory4) {
        Link link1 = new Link();
        link1.distance = 2;
        link1.neighbour = factory1;
        Link link2 = new Link();
        link2.distance = 2;
        link2.neighbour = factory4;
        return new ArrayList<>(Arrays.asList(link1, link2));
    }

    public static List<Link> initFactory3Neighbours(Factory factory1, Factory factory4) {
        Link link1 = new Link();
        link1.distance = 1;
        link1.neighbour = factory1;
        Link link2 = new Link();
        link2.distance = 2;
        link2.neighbour = factory4;
        return new ArrayList<>(Arrays.asList(link1, link2));
    }

    public static List<Link> initFactory4Neighbours(Factory factory1, Factory factory2, Factory factory3) {
        Link link1 = new Link();
        link1.distance = 3;
        link1.neighbour = factory1;
        Link link2 = new Link();
        link2.distance = 2;
        link2.neighbour = factory2;
        Link link3 = new Link();
        link3.distance = 2;
        link3.neighbour = factory3;
        return new ArrayList<>(Arrays.asList(
                link1,
                link2,
                link3
        ));
    }


}
