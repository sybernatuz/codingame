package main.java.merge_tool;


import main.java.merge_tool.utils.FileUtils;
import main.java.merge_tool.utils.LoggerUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Launcher {

    private static final String MERGE_DIR = "\\_merged\\";
    private static final String MERGE_TOOL_DIR = "merge_tool";

    public static void main(String[] args) throws Exception {
        String dir =  System.getProperty("user.dir") + "\\src\\main\\java\\compete\\";

        File[] directories = new File(dir).listFiles(File::isDirectory);
        if (directories == null)
            throw new Exception("No dir");

        List<File> projects = Stream.of(directories)
                .filter(file -> !file.getName().equals(MERGE_TOOL_DIR))
                .collect(Collectors.toList());

        projects.forEach(file -> {
            try {
                merge(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        LoggerUtils.logTitle("Finish");
    }

    private static void merge(String dir) throws Exception {
        String rootFileName = "Player";
        List<Path> paths = scanFiles(dir);

        Path rootFilePath = getRootFilePath(paths, rootFileName);
        paths.remove(rootFilePath);
        File mergedFile = FileUtils.createDirAndFile(dir, rootFileName);

        List<String> lines = new ArrayList<>();
        List<String> imports = new ArrayList<>();

        LoggerUtils.logTitle("Compute lines and imports");
        FileUtils.computeLineAndImport(rootFilePath.toFile(), lines, imports);

        paths.stream()
                .map(Path::toFile)
                .forEach(file -> FileUtils.computeLineAndImport(file, lines, imports));


        LoggerUtils.logTitle("Write file");

        FileUtils.wireToFile(mergedFile, lines, imports);

    }

    private static List<Path> scanFiles(String dir) throws IOException {
        LoggerUtils.logTitle("Scan files");
        List<Path> paths = Files.walk(Paths.get(dir))
                .filter(path -> !path.toString().contains(MERGE_DIR))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        paths.forEach(System.out::println);
        return paths;
    }

    private static Path getRootFilePath(List<Path> paths, String rootFileName) {
        Path rootFilePath = getPath(paths, rootFileName + ".java");
        LoggerUtils.logTitle("Root file : " + rootFilePath);
        return rootFilePath;
    }

    private static Path getPath(List<Path> paths, String fileName) {
        return paths.stream()
                .filter(path -> path.getFileName().toString().equals(fileName))
                .findFirst()
                .orElse(null);
    }
}
