package strategies;

import objects.Coordinate;
import objects.Game;
import objects.actions.Action;
import objects.actions.Type;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class SonarStrategy {

    private static final SonarStrategy INSTANCE = new SonarStrategy();

    public static SonarStrategy getInstance() {
        return INSTANCE;
    }

    public Optional<Action> process() {
        if (Game.getInstance().sonarCooldown > 0) {
            return Optional.empty();
        }

        if (Game.getInstance().enemySubmarine.possibleLocation.size() <= 50) {
            return Optional.empty();
        }


        return Game.getInstance().enemySubmarine.possibleLocation.stream()
                .collect(Collectors.groupingBy(Coordinate::computeSector))
                .entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .filter(entry -> entry.getValue().size() > 15)
                .map(entry -> {
                    Action action = new Action();
                    action.type = Type.SONAR;
                    action.sector = entry.getKey();
                    return action;
                });
    }
}
