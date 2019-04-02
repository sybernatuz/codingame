package main.java.merge_tool.strategies.impl;

import main.java.merge_tool.strategies.AbstractMergeStrategy;

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
        if (line.contains("public ") && !line.contains("public static void main"))
            line = line.replace("public ", "");
        if (!line.isEmpty())
            lines.add(line);
    }

    private static String getSubString(String line, String start) {
        return line.substring(line.indexOf(start), line.indexOf(";") +1);
    }
}
