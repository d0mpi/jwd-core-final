package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.util.WriteStream;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MissionWriteStream implements WriteStream {
    private static class SingletonHolder {
        private static final MissionWriteStream instance = new MissionWriteStream();
    }

    public static MissionWriteStream getInstance() {
        return MissionWriteStream.SingletonHolder.instance;
    }

    private MissionWriteStream() {
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
    }
}
