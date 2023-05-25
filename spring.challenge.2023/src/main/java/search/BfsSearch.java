package search;


import objects.Cell;
import objects.Graph;

import java.util.Optional;

public interface BfsSearch {

    Optional<Cell> search(Graph graph, Cell source);
}
