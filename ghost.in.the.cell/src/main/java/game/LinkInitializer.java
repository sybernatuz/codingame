package game;

import objects.Factory;
import objects.Link;

import java.util.Optional;
import java.util.Scanner;

class LinkInitializer {

    private static final LinkInitializer INSTANCE = new LinkInitializer();

    public static LinkInitializer getInstance() {
        return INSTANCE;
    }

    public void initialize(Scanner in) {
        int linkCount = in.nextInt(); // the number of links between factories
        for (int i = 0; i < linkCount; i++) {
            addLink(in);
        }
    }

    private void addLink(Scanner in) {
        int factory1Id = in.nextInt();
        int factory2Id = in.nextInt();
        int distance = in.nextInt();
        Factory factory1 = getOrAdd(factory1Id);
        Factory factory2 = getOrAdd(factory2Id);
        addNewLink(factory1, factory2, distance);
        addNewLink(factory2, factory1, distance);
    }

    private Factory getOrAdd(int id) {
        return Game.getInstance().factories.stream()
                .filter(factory -> factory.id == id)
                .findFirst()
                .orElseGet(() -> {
                    Factory factory = new Factory(id);
                    Game.getInstance().factories.add(factory);
                    return factory;
                });
    }

    private void addNewLink(Factory source, Factory neighbour, int distance) {
        Link link = new Link();
        link.distance = distance;
        link.neighbour = neighbour;
        source.neighbours.add(link);
    }
}
