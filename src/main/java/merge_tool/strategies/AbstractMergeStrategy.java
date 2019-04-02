package main.java.merge_tool.strategies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractMergeStrategy implements IMergeStrategy {

    @Override
    public void computeLinesAndImports(Path path, List<String> lines, List<String> imports) {
        try {
            Files.lines(path).forEach(line -> removeUselessContent(line, lines, imports));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void removeUselessContent(String line, List<String> lines, List<String> imports);
}
