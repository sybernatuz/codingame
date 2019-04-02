package main.java.merge_tool.strategies;

import java.nio.file.Path;
import java.util.List;

public interface IMergeStrategy {

    void computeLinesAndImports(Path path, List<String> lines, List<String> imports);
}
