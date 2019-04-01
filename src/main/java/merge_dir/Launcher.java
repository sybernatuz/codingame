package main.java.merge_dir;


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

        System.out.println("----------");
        System.out.println("Finish");
        System.out.println("----------");
    }

    private static void merge(String dir) throws Exception {
        String rootFileName = "Player.java";
        List<Path> paths = scanFiles(dir);

        Path rootFilePath = getRootFilePath(paths, rootFileName);

        createDir(dir);


        File mergedFile = new File(dir + MERGE_DIR + rootFileName);
        deleteExistingFile(mergedFile);

        createFile(mergedFile, dir);

        addRootFileContent(mergedFile, rootFilePath, paths);

        addOtherFilesContent(mergedFile, paths);

        System.out.println("----------");
        System.out.println("Clean file");
        System.out.println("----------");
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
                if (line.contains("public class"))
                    line = line.replace("public class", "class");
                if (line.contains("public enum"))
                    line = line.replace("public enum", "enum");
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
        System.out.println("----------");
        System.out.println("Scan files");
        System.out.println("----------");
        List<Path> paths = Files.walk(Paths.get(dir))
                .filter(path -> !path.toString().contains(MERGE_DIR))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        paths.forEach(System.out::println);
        return paths;
    }

    private static Path getRootFilePath(List<Path> paths, String rootFileName) {
        Path rootFilePath = getPath(paths, rootFileName);
        System.out.println("----------");
        System.out.println("Root file : " + rootFilePath);
        System.out.println("----------");
        return rootFilePath;
    }

    private static void createDir(String dir) throws Exception {
        File directory = new File(dir + MERGE_DIR);
        if (!directory.exists()){
            boolean successCreateDir = directory.mkdir();
            if (!successCreateDir)
                throw new Exception("Dir can not be created");

            System.out.println("----------");
            System.out.println("Directory merged created at : " + dir);
            System.out.println("----------");
        }
    }

    private static void deleteExistingFile(File mergedFile) throws Exception {
        if (mergedFile.exists()) {
            System.out.println("----------");
            System.out.println("Delete existing file : " + mergedFile.getAbsolutePath());
            System.out.println("----------");
            boolean successDeleteFile = mergedFile.delete();
            if (!successDeleteFile)
                throw new Exception("File cannot be deleted");

            System.out.println("File deleted");
        }
    }

    private static void createFile(File mergedFile, String dir) throws Exception {
        System.out.println("----------");
        System.out.println("Create file at : " + dir + MERGE_DIR);
        System.out.println("----------");
        boolean successCreateFile = mergedFile.createNewFile();
        if (!successCreateFile)
            throw new Exception("File can not be created");

        System.out.println("File created");
    }

    private static void addRootFileContent(File mergedFile, Path rootFilePath, List<Path> paths) throws IOException {
        System.out.println("----------");
        System.out.println("Add root file content");
        System.out.println("----------");
        IOCopy.joinFiles(mergedFile, Collections.singletonList(rootFilePath.toFile()).toArray(new File[0]));
        paths.remove(rootFilePath);
    }

    private static void addOtherFilesContent(File mergedFile, List<Path> paths) throws IOException {
        System.out.println("----------");
        System.out.println("Add other files content");
        System.out.println("----------");
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
