package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public final class PropertyReaderUtil {

    @Getter
    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    static {
        loadProperties();
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    private static void loadProperties() {
        final String propertiesFileName = "src/main/resources/application.properties";

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            fileInputStream.close();
            log.info("Properties have been reading successfully");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Properties have not been reading");
        }
    }
}
