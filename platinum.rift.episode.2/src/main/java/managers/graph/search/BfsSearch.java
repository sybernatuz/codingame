package managers.graph.search;

import objects.Graph;
import objects.Path;
import objects.Zone;

import java.util.Optional;

public interface BfsSearch {

    Optional<Path> search(Graph graph, Zone source);
}
