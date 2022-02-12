package strategies;

import beans.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RadarStrategy implements Strategy {


    @Override public Optional<Action> computeAction(Board board, Entity robot) {
        if (board.myRadarCooldown == 0) {
            Action action = Action.request(EntityType.RADAR);
            action.message = "Buy radar";
            return Optional.of(action);
        }
        if (EntityType.RADAR.equals(robot.item)) {
            Optional<Coord> newRadarCoord = board.myRadarPos.stream()
                    .max(Comparator.comparing(coord -> coord.x))
                    .map(coord -> new Coord(coord.x + 5, coord.y));

            if (!newRadarCoord.isPresent()) {
                return Optional.empty();
            }

            Action action = Action.dig(newRadarCoord.get());
            action.message = "Install radar";
            return Optional.of(action);
        }
        return Optional.empty();
    }
}
