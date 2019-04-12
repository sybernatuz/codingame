package main.java.compete.ghost_in_the_cell.managers;

import main.java.compete.ghost_in_the_cell.enums.EntityTypeEnum;
import main.java.compete.ghost_in_the_cell.objects.Bomb;
import main.java.compete.ghost_in_the_cell.objects.Factory;
import main.java.compete.ghost_in_the_cell.objects.Link;
import main.java.compete.ghost_in_the_cell.objects.Troop;

import java.util.List;
import java.util.Scanner;

public class EntityManager {

    public void updateEntity(Scanner in, List<Troop> troops, List<Factory> factories, List<Bomb> bombs) {
        int entityId = in.nextInt();
        String entityType = in.next();
        switch (EntityTypeEnum.valueOf(entityType)) {
            case TROOP:
                Troop troop = new Troop(in, entityId);
                troops.add(troop);
                break;
            case FACTORY:
                Factory factoryData = new Factory(in);
                factories.stream()
                        .filter(factory -> factory.id == entityId)
                        .findFirst()
                        .ifPresent(factory -> factory.update(factoryData));
                factories.stream()
                        .map(factory -> factory.neighbours)
                        .flatMap(List::stream)
                        .map(link -> link.neighbour)
                        .filter(neighbour -> neighbour.id == entityId)
                        .forEach(neighbour -> neighbour.update(factoryData));
                break;
            case BOMB:
                Bomb bomb = new Bomb(in, entityId);
                bombs.add(bomb);
                break;
        }
    }

    public void addLink(Scanner in, List<Factory> factories) {
        int factory1Id = in.nextInt();
        int factory2Id = in.nextInt();
        int distance = in.nextInt();
        Factory factory1 = createFactory(factories, factory1Id);
        Factory factory2 = createFactory(factories, factory2Id);
        computeLink(factories, factory1, factory2, distance);
        computeLink(factories, factory2, factory1, distance);
    }

    private void computeLink(List<Factory> factories, Factory source, Factory neighbour, int distance) {
        factories.stream()
                .filter(factory -> factory.equals(source))
                .findFirst()
                .ifPresentOrElse(
                        factory -> addNewLink(distance, factory, neighbour),
                        () -> addFactory(factories, distance, source, neighbour)
                );
    }

    private Factory createFactory(List<Factory> factories, int id) {
        return factories.stream()
                .filter(factory -> factory.id == id)
                .findFirst()
                .orElse(new Factory(id));
    }

    private void addFactory(List<Factory> factories, int distance, Factory factory, Factory neighbour) {
        addNewLink(distance, factory, neighbour);
        factories.add(factory);
    }

    private void addNewLink(int distance, Factory source, Factory neighbour) {
        Link link = new Link();
        link.distance = distance;
        link.neighbour = neighbour;
        source.neighbours.add(link);
    }
}
