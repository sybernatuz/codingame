package strategies;

import beans.Action;
import beans.Board;
import beans.Entity;
import beans.EntityType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultStrategy implements Strategy {

    @Override public Optional<Action> computeAction(Board board, Entity robot) {
        return board.entitiesById.values()
                .stream()
                .filter(entity -> entity.type.equals(EntityType.AMADEUSIUM))
                .findFirst()
                .map(ore -> Action.dig(ore.pos));
    }
}
