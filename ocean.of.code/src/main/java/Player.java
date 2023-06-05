import analyzers.LocationAnalyzer;
import objects.Coordinate;
import objects.Game;
import objects.Grid;
import objects.actions.Action;
import pathfinder.PathFinder;
import strategies.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Grid.getInstance().sized(in);
        int myId = in.nextInt();

        if (in.hasNextLine()) {
            in.nextLine();
        }

        for (int i = 0; i < Grid.getInstance().height; i++) {
            Grid.getInstance().initialize(in.nextLine(), i);
        }

        Game.getInstance().enemySubmarine.initPossibleLocation();
        Game.getInstance().mySubmarine.initPossibleLocation();

        Game.getInstance().bestPath = PathFinder.getInstance().findPath();
        System.err.println("Path : " + Game.getInstance().bestPath.size());

        Coordinate start = Game.getInstance().bestPath.get(0);
        System.out.println(start.x + " " + start.y);

        // game loop
        while (true) {
            LocalDateTime startTime = LocalDateTime.now();
            LocationAnalyzer.getInstance().process(Game.getInstance().mySubmarine);

            Game.getInstance().update(in);
            if (in.hasNextLine()) {
                in.nextLine();
            }
            Game.getInstance().updateOrder(in);

            System.err.println("0 " + startTime);
            System.err.println("1 " + LocalDateTime.now());

            LocationAnalyzer.getInstance().processByOtherSubmarineActions(Game.getInstance().mySubmarine, Game.getInstance().enemySubmarine);
            System.err.println("1.5 " + LocalDateTime.now());
            LocationAnalyzer.getInstance().processByOtherSubmarineActions(Game.getInstance().enemySubmarine, Game.getInstance().mySubmarine);
            LocationAnalyzer.getInstance().process(Game.getInstance().enemySubmarine);
            System.err.println("2 " + LocalDateTime.now());

            List<Action> actions = computeActions();
            System.err.println("3 " + LocalDateTime.now());

            StringJoiner output = new StringJoiner(" | ");
            actions.forEach(action -> output.add(action.toString()));

            output.add(setMessageToPrint());
            System.err.println("4 " + LocalDateTime.now());

            System.out.println(output);

            System.err.println(Game.getInstance().enemySubmarine.possibleLocation.size());
            System.err.println(Game.getInstance().enemySubmarine.possibleLocation.stream().mapToLong(possibleLocation -> possibleLocation.histories.size()).sum());
            System.err.println(Game.getInstance().enemySubmarine.possibleLocation.stream().flatMap(possibleLocation -> possibleLocation.histories.stream()).mapToLong(historic -> historic.coordinates.size()).sum());

            log(startTime);
            Game.getInstance().mySubmarine.orders = actions;
            Game.getInstance().turn++;
        }
    }

    private static List<Action> computeActions() {
        System.err.println("2.1 " + LocalDateTime.now());
        List<Action> assassin = AssassinStrategy.getInstance().process();
        if (!assassin.isEmpty()) {
            return assassin;
        }

        List<Action> actions = new ArrayList<>();
        System.err.println("2.2 " + LocalDateTime.now());
        MineStrategy.getInstance().process()
                .ifPresent(actions::add);
        System.err.println("2.3 " + LocalDateTime.now());
        TorpedoStrategy.getInstance().process()
                .ifPresent(actions::add);
        System.err.println("2.4 " + LocalDateTime.now());
        TriggerStrategy.getInstance().process()
                .ifPresent(actions::add);
        System.err.println("2.5 " + LocalDateTime.now());
        SonarStrategy.getInstance().process()
                .ifPresent(actions::add);
        System.err.println("2.6 " + LocalDateTime.now());

        actions.add(MoveStrategyManager.getInstance().process(actions));
        return actions;
    }

    private static String setMessageToPrint() {

        String values =  new StringJoiner(":")
                .add(String.valueOf(Game.getInstance().enemySubmarine.possibleLocation.size()))
                .add(String.valueOf(Game.getInstance().mySubmarine.possibleLocation.size()))
                .toString();
        return "MSG " + values;
    }

    private static void log(LocalDateTime startTime) {
        if (Game.getInstance().enemySubmarine.possibleLocation.size() < 10) {
            System.err.println("--- ENEMY ---");
            Game.getInstance().enemySubmarine.possibleLocation
                    .forEach(System.err::println);
            System.err.println("---");
        }
        if (Game.getInstance().mySubmarine.possibleLocation.size() < 10) {
            System.err.println("--- MINE ---");
            Game.getInstance().mySubmarine.possibleLocation
                    .forEach(System.err::println);
            System.err.println("---");
        }
        System.err.println("Turn compute duration : " + Duration.between(startTime, LocalDateTime.now()).toString());
    }
}