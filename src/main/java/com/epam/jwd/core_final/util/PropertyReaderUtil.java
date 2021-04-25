package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PropertyReaderUtil {

    @Getter
    private static final Properties properties = new Properties();

    private PropertyReaderUtil(){}
    static {
        try {
            loadProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    private static void loadProperties() throws FileNotFoundException {
        final String propertiesFileName = "src/main/resources/application.properties";

        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
