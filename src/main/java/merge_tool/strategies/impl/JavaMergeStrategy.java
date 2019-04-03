package main.java.merge_tool.strategies.impl;

import main.java.merge_tool.strategies.AbstractMergeStrategy;

import java.util.Arrays;
import java.util.List;

public class JavaMergeStrategy extends AbstractMergeStrategy {

    @Override
    protected void removeUselessContent(String line, List<String> lines, List<String> imports) {
        if (line.contains("package"))
            line = line.replace(getSubString(line, "package"), "");

        if (line.contains("import")) {
            String lineImport = getSubString(line, "import");
            if (!lineImport.contains("main.java") && !imports.contains(lineImport))
                imports.add(lineImport);

            line = line.replace(lineImport, "");
        }
        if (line.contains("public ") && !isLineisIgnoredPublicPattern(line))
            line = line.replace("public ", "");
        if (!line.isEmpty())
            lines.add(line);
    }

    private static String getSubString(String line, String start) {
        return line.substring(line.indexOf(start), line.indexOf(";") +1);
    }

    private static boolean isLineisIgnoredPublicPattern(String line) {
        return getIgnoredPublicPatterns().stream()
                .anyMatch(line::contains);
    }

    private static List<String> getIgnoredPublicPatterns() {
        return Arrays.asList(
                "public String toString()",
                "public static void main"
        );
    }
}
