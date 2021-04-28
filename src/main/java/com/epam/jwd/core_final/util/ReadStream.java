package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public interface ReadStream {
    void readData();

    default String buildFilePath(String fileName) {
        char fileSeparator = File.separatorChar;
        return "src" + fileSeparator +
                "main" + fileSeparator +
                "resources" + fileSeparator +
                ApplicationProperties.getInstance().getInputRootDir() + fileSeparator +
                fileName;
    }

    default String readFileToString(String fileName) {
        Path path = Paths.get(buildFilePath(fileName));
        Scanner scanner;
        StringBuilder line = new StringBuilder();
        try {
            scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
                if (!temp.matches("#.*")) {
                    line.append(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.toString();
    }
}
