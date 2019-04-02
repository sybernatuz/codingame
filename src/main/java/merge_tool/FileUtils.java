package main.java.merge_tool;

import java.io.File;

public class FileUtils {

    private static final String MERGE_DIR = "\\_merged\\";

    public static File createDirAndFile(String dir, String rootFileName) {
        File mergedFile = null;
        try {
            createDir(dir);
            mergedFile = new File(dir + MERGE_DIR + rootFileName);
            deleteExistingFile(mergedFile);
            createFile(mergedFile, dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mergedFile;
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
        LoggerUtils.logTitle("Delete exising file if not exists");
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

}
