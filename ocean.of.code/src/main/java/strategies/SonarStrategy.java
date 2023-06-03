package strategies;

import objects.Coordinate;
import objects.Game;
import objects.Submarine;
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
        if (!needSonar()) {
            return Optional.empty();
        }

        return Game.getInstance().enemySubmarine.getDistinctPossibleLocation().stream()
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

    private boolean needSonar() {
        Submarine enemy = Game.getInstance().enemySubmarine;
        return Game.getInstance().sonarCooldown == 0
            && enemy.getDistinctPossibleLocation().size() > 50
            && enemy.coordinate == null;
    }
}
