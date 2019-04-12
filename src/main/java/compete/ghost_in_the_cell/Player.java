package main.java.compete.ghost_in_the_cell;

import main.java.compete.ghost_in_the_cell.managers.EntityManager;
import main.java.compete.ghost_in_the_cell.objects.Attack;
import main.java.compete.ghost_in_the_cell.objects.Bomb;
import main.java.compete.ghost_in_the_cell.objects.Factory;
import main.java.compete.ghost_in_the_cell.objects.Troop;
import main.java.compete.ghost_in_the_cell.strategies.AttackStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private static final EntityManager entityManager = new EntityManager();
    private static final AttackStrategy attackStrategy = new AttackStrategy();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        int linkCount = in.nextInt(); // the number of links between factories
        List<Factory> factories = new ArrayList<>();
        List<Troop> troops = new ArrayList<>();
        List<Bomb> bombs = new ArrayList<>();
        for (int i = 0; i < linkCount; i++) {
            entityManager.addLink(in, factories);
        }
        // game loop
        while (true) {
            troops.clear();
            bombs.clear();
            int entityCount = in.nextInt(); // the number of entities (e.g. factories and troops)
            for (int i = 0; i < entityCount; i++) {
                entityManager.updateEntity(in, troops, factories, bombs);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // Any valid action, such as "WAIT" or "MOVE source destination cyborgs"
            System.out.println(computeAction(factories, troops));
        }

    }

    private static String computeAction(List<Factory> factories, List<Troop> troops) {
        List<Attack> attacks = attackStrategy.computeAttacks(factories, troops);
        StringBuilder actions = new StringBuilder();
        if (attacks.isEmpty())
            return "WAIT";
        attacks.forEach(attack ->
                    actions.append("MOVE")
                    .append(" ")
                    .append(attack.source.id)
                    .append(" ")
                    .append(attack.target.id)
                    .append(" ")
                    .append(attack.number)
                    .append(";")
        );
        actions.append("MSG attack");
        return actions.toString();
    }
}