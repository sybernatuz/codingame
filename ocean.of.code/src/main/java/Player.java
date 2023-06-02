import analyzers.LocationAnalyzer;
import objects.Coordinate;
import objects.Game;
import objects.Grid;
import objects.actions.Action;
import pathfinder.PathFinder;
import strategies.*;

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

        Coordinate start = Game.getInstance().bestPath.get(0);
        System.out.println(start.x + " " + start.y);
        // game loop
        while (true) {
            Game.getInstance().update(in);
            if (in.hasNextLine()) {
                in.nextLine();
            }
            Game.getInstance().updateOrder(in);

            LocationAnalyzer.getInstance().process(Game.getInstance().enemySubmarine, Game.getInstance().mySubmarine);
            LocationAnalyzer.getInstance().process(Game.getInstance().mySubmarine, Game.getInstance().enemySubmarine);

            List<Action> actions = new ArrayList<>();
            MineStrategy.getInstance().process()
                    .ifPresent(actions::add);
            TorpedoStrategy.getInstance().process()
                    .ifPresent(actions::add);
            TriggerStrategy.getInstance().process()
                    .ifPresent(actions::add);
            SonarStrategy.getInstance().process()
                    .ifPresent(actions::add);

            actions.add(MoveStrategyManager.getInstance().process());


            Game.getInstance().mySubmarine.orders = actions;

            StringJoiner output = new StringJoiner(" | ");
            actions.forEach(action -> output.add(action.toString()));

            output.add(setMessageToPrint());
            System.out.println(output);
            Game.getInstance().turn++;
        }
    }

    private static String setMessageToPrint() {
        if (Game.getInstance().enemySubmarine.possibleLocation.size() < 10) {
            Game.getInstance().enemySubmarine.possibleLocation.forEach(System.err::println);
        }
        String values =  new StringJoiner(":")
                .add(String.valueOf(Game.getInstance().mySubmarine.coordinateFinal.x))
                .add(String.valueOf(Game.getInstance().mySubmarine.coordinateFinal.y))
                .add(String.valueOf(Game.getInstance().enemySubmarine.possibleLocation.size()))
                .add(String.valueOf(Game.getInstance().mySubmarine.possibleLocation.size()))
                .toString();
        return "MSG " + values;
    }
}