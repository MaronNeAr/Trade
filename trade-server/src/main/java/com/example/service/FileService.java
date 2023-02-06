package com.example.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {
    public void writeToFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtils.writeStringToFile(file, content, "UTF-8");
    }

    public String readFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        return FileUtils.readFileToString(file, "UTF-8");
    }
}
