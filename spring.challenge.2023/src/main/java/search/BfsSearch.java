package search;


import objects.Path;
import objects.Zone;
import objects.Graph;

import java.util.Optional;

public interface BfsSearch {

    Optional<Path> search(Graph graph, Zone source);
    Optional<Path> search(Graph graph, Zone source, Zone target);
}
