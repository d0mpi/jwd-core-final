package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.util.WriteStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class MissionsSerializeToFileStream implements WriteStream {
    private static class SingletonHolder {
        private static final MissionsSerializeToFileStream instance = new MissionsSerializeToFileStream();
    }

    public static MissionsSerializeToFileStream getInstance() {
        return MissionsSerializeToFileStream.SingletonHolder.instance;
    }

    private MissionsSerializeToFileStream() {
    }

    @Override
    public void writeMissionsResult(List<FlightMission> flightMissions) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        char fileSeparator = File.separatorChar;
        File file = new File(
                "src" + fileSeparator +
                        "main" + fileSeparator +
                        "resources" + fileSeparator +
                        ApplicationProperties.getInstance().getOutputRootDir() + fileSeparator +
                        ApplicationProperties.getInstance().getMissionsFileName() + ".json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, flightMissions);
        log.info("Mission " + flightMissions + " was written to file");
    }
}
