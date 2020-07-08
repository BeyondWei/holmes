package com.shuzheng.holmes.core.entrance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataOffset {

    private final static String FILE_TEMP_PATH = "/mnt/data/log/offset/temp/";
    private final static String FILE_PATH = "/mnt/data/log/offset/file/";

    private DataOffset() {
    }

    public static void creatFile(String fileType, String fileName, String msg) {
        String fileTempPath = fileType + "-" + fileName;
        File file = new File(FILE_TEMP_PATH + fileTempPath);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            FileWriter fileWritter = new FileWriter(file, true);
            fileWritter.write(msg);
            fileWritter.close();
            File file1 = new File(FILE_PATH + fileTempPath);
            file1.getParentFile().mkdirs();
            file.renameTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String fileType, String fileName) {
        String fileTempPath = fileType + "-" + fileName;
        File file = new File(FILE_PATH + fileTempPath);
        file.delete();
    }
}
