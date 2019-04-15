import adapters.MergeStrategyAdapter;
import constants.DirConstants;
import strategies.IMergeStrategy;
import utils.FileUtils;
import utils.LoggerUtils;

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

    private static IMergeStrategy mergeStrategy;

    public static void main(String[] args) throws Exception {
        List<File> projects = getAllProjects();

        projects.forEach(file -> {
            try {
                merge(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        LoggerUtils.logTitle("Finish");
    }

    private static List<File> getAllProjects() throws Exception {
        String dir =  System.getProperty("user.dir");
        File[] directories = new File(dir).listFiles(File::isDirectory);
        if (directories == null)
            throw new Exception("No projects found");
        return Stream.of(directories)
                .filter(file -> !file.getName().equals(DirConstants.MERGE_TOOL_DIR))
                .filter(file -> file.listFiles() != null)
                .flatMap(file -> Stream.of(file.listFiles()))
                .filter(file -> file.getName().equals("src"))
                .map(File::getParentFile)
                .collect(Collectors.toList());
    }

    private static void merge(String dir) throws Exception {
        LoggerUtils.logTitle("Project : " + dir);

        String filesToMergeDir = dir + DirConstants.SOURCE_FILES_DIR;
        Path rootFilePath = getRootFilePath(filesToMergeDir);
        mergeStrategy = new MergeStrategyAdapter().getMergeStrategy(rootFilePath);
        List<Path> paths = scanFiles(filesToMergeDir, rootFilePath);


        List<String> lines = new ArrayList<>();
        List<String> imports = new ArrayList<>();


        LoggerUtils.logTitle("Compute lines and imports for root file");
        mergeStrategy.computeLinesAndImports(rootFilePath, lines, imports);

        LoggerUtils.logTitle("Compute lines and imports for other files");
        paths.forEach(path -> mergeStrategy.computeLinesAndImports(path, lines, imports));

        File mergedFile = FileUtils.createDirAndFile(dir, rootFilePath.getFileName().toString());
        FileUtils.writeToFile(mergedFile, lines, imports);
    }

    private static List<Path> scanFiles(String dir, Path rootFilePath) throws IOException {
        LoggerUtils.logTitle("Scan files");
        List<Path> paths = Files.walk(Paths.get(dir))
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
