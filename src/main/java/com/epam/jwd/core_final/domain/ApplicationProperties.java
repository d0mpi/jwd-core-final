package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;
import lombok.Getter;
import lombok.Value;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
@Value
public class ApplicationProperties {
    @Getter
    String inputRootDir;
    @Getter
    String outputRootDir;
    @Getter
    String crewFileName;
    @Getter
    String missionsFileName;
    @Getter
    String spaceshipsFileName;
    @Getter
    String spaceMapFileName;
    @Getter
    Integer fileRefreshRate;
    @Getter
    String dateTimeFormat;
    @Getter
    String timetableFileName;


    private static class SingletonHolder {
        private static final ApplicationProperties instance = new ApplicationProperties();
    }
    public static ApplicationProperties getInstance() {
        return SingletonHolder.instance;
    }

    private ApplicationProperties() {
        this.inputRootDir =  PropertyReaderUtil.getProperties().getProperty("inputRootDir");
        this.outputRootDir = PropertyReaderUtil.getProperties().getProperty("outputRootDir");
        this.crewFileName =  PropertyReaderUtil.getProperties().getProperty("crewFileName");
        this.missionsFileName =  PropertyReaderUtil.getProperties().getProperty("missionsFileName");
        this.spaceshipsFileName =  PropertyReaderUtil.getProperties().getProperty("spaceshipsFileName");
        this.spaceMapFileName =  PropertyReaderUtil.getProperties().getProperty("spaceMapFileName");
        this.fileRefreshRate =  Integer.parseInt(PropertyReaderUtil.getProperties().getProperty("fileRefreshRate"));
        this.dateTimeFormat =  PropertyReaderUtil.getProperties().getProperty("dateTimeFormat");
        this.timetableFileName =  PropertyReaderUtil.getProperties().getProperty("timetableFileName");
    }
}
