import game.GameManager;
import strategies.StrategiesManager;

import java.util.Scanner;
import java.util.StringJoiner;

class Player {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        GameManager.getInstance().initialize(in);

        while (true) {
            GameManager.getInstance().update(in);
            System.out.println(computeActions());
        }
    }

    private static String computeActions() {
        StringJoiner stringJoiner = new StringJoiner(";");
        StrategiesManager.getInstance().process()
                .forEach(action -> stringJoiner.add(action.toString()));

        if (stringJoiner.length() == 0) {
            return "WAIT";
        }
        return stringJoiner.toString();
    }
}