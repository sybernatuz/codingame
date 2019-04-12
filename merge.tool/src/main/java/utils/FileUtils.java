package utils;

import constants.DirConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static File createDirAndFile(String dir, String rootFileName) {
        File mergedFile = null;
        String mergeDir = dir + DirConstants.MERGE_DIR;
        try {
            createDir(mergeDir);
            mergedFile = new File(mergeDir + rootFileName);
            deleteExistingFile(mergedFile);
            createFile(mergedFile, mergeDir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mergedFile;
    }

    public static void writeToFile(File mergedFile, List<String> lines, List<String> imports) throws IOException {
        LoggerUtils.logTitle("Write file");
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
        File directory = new File(dir);
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
        LoggerUtils.logTitle("Create file at : " + dir);
        boolean successCreateFile = mergedFile.createNewFile();
        if (!successCreateFile)
            throw new Exception("File can not be created");

        LoggerUtils.log("File created : " + mergedFile.getName());
    }

}
