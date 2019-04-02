package main.java.merge_tool;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Launcher {

    private static final String MERGE_DIR = "\\_merged\\";

    public static void main(String[] args) throws Exception {
        String dir =  System.getProperty("user.dir") + "\\src\\main\\java\\compete\\";

        File[] directories = new File(dir).listFiles(File::isDirectory);
        if (directories == null)
            throw new Exception("No dir");

        List<File> projects = Stream.of(directories)
                .filter(file -> !file.getName().equals("merge_dir"))
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
        String rootFileName = "Player.java";
        List<Path> paths = scanFiles(dir);

        Path rootFilePath = getRootFilePath(paths, rootFileName);

        File mergedFile = FileUtils.createDirAndFile(dir, rootFileName);

        addRootFileContent(mergedFile, rootFilePath, paths);
        addOtherFilesContent(mergedFile, paths);

        LoggerUtils.logTitle("Clean file");
        BufferedReader input = null;
        List<String> lines = new ArrayList<>();
        List<String> imports = new ArrayList<>();
        try {
            input = new BufferedReader(new FileReader(mergedFile));
            String line = input.readLine();
            while (line != null) {
                if (line.contains("package")) {
                    line = line.replace(getSubString(line, "package"), "");
                }
                if (line.contains("import")) {
                    String lineImport = getSubString(line, "import");
                    if (!lineImport.contains("main.java")) {
                        imports.add(lineImport);
                    }
                    line = line.replace(lineImport, "");
                }
                if (line.contains("public "))
                    line = line.replace("public ", "");
                if (!line.isEmpty())
                    lines.add(line);
                line = input.readLine();
            }
        } finally {
            IOUtils.closeQuietly(input);
        }

        deleteExistingFile(mergedFile);

        createFile(mergedFile, dir);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mergedFile));
        imports.forEach(s -> {
            try {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lines.forEach(s -> {
            try {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bufferedWriter.close();

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
        Path rootFilePath = getPath(paths, rootFileName);
        LoggerUtils.logTitle("Root file : " + rootFilePath);
        return rootFilePath;
    }

    private static void addRootFileContent(File mergedFile, Path rootFilePath, List<Path> paths) throws IOException {
        LoggerUtils.logTitle("Add root file content");
        IOCopy.joinFiles(mergedFile, Collections.singletonList(rootFilePath.toFile()).toArray(new File[0]));
        paths.remove(rootFilePath);
    }

    private static void addOtherFilesContent(File mergedFile, List<Path> paths) throws IOException {
        LoggerUtils.logTitle("Add other files content");
        File[] files = paths.stream()
                .map(path -> new File(path.toString()))
                .toArray(File[]::new);
        Stream.of(files).forEach(System.out::println);
        IOCopy.joinFiles(mergedFile, files);
    }


    private static Path getPath(List<Path> paths, String fileName) {
        return paths.stream()
                .filter(path -> path.getFileName().toString().equals(fileName))
                .findFirst()
                .orElse(null);
    }

    private static String getSubString(String line, String start) {
        return line.substring(line.indexOf(start), line.indexOf(";") +1);
    }
}
