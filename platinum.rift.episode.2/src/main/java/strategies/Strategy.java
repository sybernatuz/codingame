package strategies;

import objects.Graph;
import objects.Move;

import java.util.List;

public interface Strategy {

    List<Move> computeMoves(Graph graph);
}
