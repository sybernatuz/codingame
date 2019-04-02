package main.java.merge_tool.utils;

import java.io.*;
import java.util.List;

public class FileUtils {

    private static final String MERGE_DIR = "\\_merged\\";

    public static File createDirAndFile(String dir, String rootFileName) {
        File mergedFile = null;
        try {
            createDir(dir);
            mergedFile = new File(dir + MERGE_DIR + rootFileName + ".txt");
            deleteExistingFile(mergedFile);
            createFile(mergedFile, dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mergedFile;
    }

    public static void computeLineAndImport(File file, List<String> lines, List<String> imports) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(file));
            String line = input.readLine();
            while (line != null) {
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
                line = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public static void wireToFile(File mergedFile, List<String> lines, List<String> imports) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mergedFile));
        imports.forEach(s -> write(bufferedWriter, s));
        lines.forEach(s -> write(bufferedWriter, s));
        bufferedWriter.close();
    }

    private static void write(BufferedWriter bufferedWriter, String line) {
        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDir(String dir) throws Exception {
        LoggerUtils.logTitle("Create Dir if not exists");
        File directory = new File(dir + MERGE_DIR);
        if (!directory.exists()){
            LoggerUtils.log("Create dir at : " + dir);
            boolean successCreateDir = directory.mkdir();
            if (!successCreateDir)
                throw new Exception("Dir can not be created");

            LoggerUtils.log("Directory created at : " + dir);
        }
    }

    private static void deleteExistingFile(File mergedFile) throws Exception {
        LoggerUtils.logTitle("Delete existing file if exists");
        if (mergedFile.exists()) {
            LoggerUtils.log("Delete existing file : " + mergedFile.getAbsolutePath());
            boolean successDeleteFile = mergedFile.delete();
            if (!successDeleteFile)
                throw new Exception("File cannot be deleted");

            LoggerUtils.log("File deleted : " + mergedFile.getName());
        }
    }

    private static void createFile(File mergedFile, String dir) throws Exception {
        LoggerUtils.logTitle("Create file at : " + dir + MERGE_DIR);
        boolean successCreateFile = mergedFile.createNewFile();
        if (!successCreateFile)
            throw new Exception("File can not be created");

        LoggerUtils.log("File created : " + mergedFile.getName());
    }

    private static String getSubString(String line, String start) {
        return line.substring(line.indexOf(start), line.indexOf(";") +1);
    }

}
