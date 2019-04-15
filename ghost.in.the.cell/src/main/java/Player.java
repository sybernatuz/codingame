
import managers.EntityManager;
import objects.Attack;
import objects.Bomb;
import objects.Factory;
import objects.Troop;
import strategies.AttackStrategy;
import strategies.BombStrategy;

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
    private static final BombStrategy bombStrategy = new BombStrategy();

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
            System.out.println(computeAction(factories, troops, bombs));
        }

    }

    private static String computeAction(List<Factory> factories, List<Troop> troops, List<Bomb> bombs) {
        StringBuilder actions = new StringBuilder();
        actions.append(computeBomb(factories, troops, bombs))
                .append(computeAttack(factories, troops))
                .append("MSG attack");
        return actions.toString();
    }

    private static String computeBomb(List<Factory> factories, List<Troop> troops, List<Bomb> bombs) {
        StringBuilder bombToLaunch = new StringBuilder();
        Attack bomb = bombStrategy.computeBomb(factories, troops, bombs);
        if (bomb == null)
            return bombToLaunch.toString();

        bombToLaunch.append("BOMB")
                .append(" ")
                .append(bomb.source.id)
                .append(" ")
                .append(bomb.target.id)
                .append(";");
        return bombToLaunch.toString();
    }

    private static String computeAttack(List<Factory> factories, List<Troop> troops) {
        StringBuilder attacksToDo = new StringBuilder();
        List<Attack> attacks = attackStrategy.computeAttacks(factories, troops);
        attacks.forEach(attack ->
                attacksToDo.append("MOVE")
                        .append(" ")
                        .append(attack.source.id)
                        .append(" ")
                        .append(attack.target.id)
                        .append(" ")
                        .append(attack.number)
                        .append(";")
        );
        return attacksToDo.toString();
    }
}