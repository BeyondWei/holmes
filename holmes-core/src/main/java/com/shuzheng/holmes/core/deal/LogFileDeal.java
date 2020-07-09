package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.common.Constants;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileDeal extends HolmesDealAbstract {


    @SneakyThrows
    @Override
    public void deal(Object msg) {
        String filePath = configContext.getString(Constants.DEAL_LOG_FILE_PATH);
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter fileWriter = null;
        try {
            file.createNewFile();
            fileWriter = new FileWriter(file, true);
            fileWriter.write("\n" + msg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert fileWriter != null;
            fileWriter.close();
        }

    }

}
