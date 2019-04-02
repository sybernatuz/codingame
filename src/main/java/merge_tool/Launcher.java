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

    public static final String MERGE_DIR = "\\_merged\\";
    private static final String MERGE_TOOL_DIR = "merge_tool";
    private static final String ROOT_DIR = "\\src\\main\\java\\compete\\";

    public static void main(String[] args) throws Exception {
        String dir =  System.getProperty("user.dir") + ROOT_DIR;

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
        LoggerUtils.logTitle("Project : " + dir);

        Path rootFilePath = getRootFilePath(dir);
        List<Path> paths = scanFiles(dir, rootFilePath);

        File mergedFile = FileUtils.createDirAndFile(dir, rootFilePath.getFileName().toString());

        List<String> lines = new ArrayList<>();
        List<String> imports = new ArrayList<>();


        LoggerUtils.logTitle("Compute lines and imports for root file");
        FileUtils.computeLineAndImport(rootFilePath, lines, imports);

        LoggerUtils.logTitle("Compute lines and imports for other files");
        paths.forEach(path -> FileUtils.computeLineAndImport(path, lines, imports));

        FileUtils.wireToFile(mergedFile, lines, imports);
    }

    private static List<Path> scanFiles(String dir, Path rootFilePath) throws IOException {
        LoggerUtils.logTitle("Scan files");
        List<Path> paths = Files.walk(Paths.get(dir))
                .filter(path -> !path.toString().contains(MERGE_DIR))
                .filter(Files::isRegularFile)
                .filter(path -> !path.getFileName().equals(rootFilePath.getFileName()))
                .collect(Collectors.toList());
        paths.forEach(System.out::println);
        return paths;
    }

    private static Path getRootFilePath(String dir) throws IOException {
        Path rootFilePath = Files.list(Paths.get(dir))
                .filter(Files::isRegularFile)
                .findFirst()
                .orElse(null);
        LoggerUtils.logTitle("Root file : " + rootFilePath);
        return rootFilePath;
    }
}
