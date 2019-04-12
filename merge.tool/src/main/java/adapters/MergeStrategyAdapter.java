package adapters;

import strategies.IMergeStrategy;
import strategies.impl.JavaMergeStrategy;

import java.nio.file.Path;
import java.util.Optional;

public class MergeStrategyAdapter {

    public IMergeStrategy getMergeStrategy(Path rootFile) {
        Optional<String> extension = findExtension(rootFile.getFileName().toString());
        if (!extension.isPresent())
            return null;

        switch (extension.get()) {
            case ".java": return new JavaMergeStrategy();
            case ".py": return null;
        }
        return null;
    }

    private static Optional<String> findExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(fileName.substring(lastIndex));
    }
}
