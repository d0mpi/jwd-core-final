package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.context.impl.ApplicationMainMenu;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

public class TimerMissionWriter extends TimerTask {

    private static class SingletonHolder {
        private static final TimerMissionWriter instance = new TimerMissionWriter();
    }

    public static TimerMissionWriter getInstance() {
        return TimerMissionWriter.SingletonHolder.instance;
    }

    private TimerMissionWriter() {}


    private final char FILESEPARATOR = File.separatorChar;
    private final File file = new File(
            "src" + FILESEPARATOR +
                    "main" + FILESEPARATOR +
                    "resources" + FILESEPARATOR +
                    ApplicationProperties.getInstance().getOutputRootDir() + FILESEPARATOR +
                    ApplicationProperties.getInstance().getTimetableFileName() + ".txt");
    private FileWriter fileWriter = null;

    @Override
    public void run() {
        try {
            fileWriter = new FileWriter(file,true);
            for(FlightMission mission : MissionServiceImpl.getInstance().findAllMissions()) {
                fileWriter.append(mission.toString());
                fileWriter.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
