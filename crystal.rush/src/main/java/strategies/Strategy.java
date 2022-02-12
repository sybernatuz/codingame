package strategies;

import beans.Action;
import beans.Board;
import beans.Entity;

import java.util.List;
import java.util.Optional;

public interface Strategy {

    Optional<Action> computeAction(Board board, Entity robot);
}
