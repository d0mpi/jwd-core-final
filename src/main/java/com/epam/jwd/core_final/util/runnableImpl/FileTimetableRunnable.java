package com.epam.jwd.core_final.util.runnableImpl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class FileTimetableRunnable implements Runnable {
    private static class SingletonHolder {
        private static final FileTimetableRunnable instance = new FileTimetableRunnable();
    }

    public static FileTimetableRunnable getInstance() {
        return FileTimetableRunnable.SingletonHolder.instance;
    }

    private FileTimetableRunnable() {
    }

    private final char FILESEPARATOR = File.separatorChar;
    private final File file = new File(
            "src" + FILESEPARATOR +
                    "main" + FILESEPARATOR +
                    "resources" + FILESEPARATOR +
                    ApplicationProperties.getInstance().getOutputRootDir() + FILESEPARATOR +
                    ApplicationProperties.getInstance().getTimetableFileName() + ".txt");

    @Override
    public void run() {
        log.info("FileTimetableRunnable is running");
        upgradeMissions();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.append("@".repeat(80)).append("\n").
                    append("@").append(" ".repeat(78)).append("@\n").
                    append("@ Earth time: ").append(String.valueOf(NassaContext.getInstance().getCurrentDate())).append(" ".repeat(55)).append("@\n").
                    append("@").append(" ".repeat(78)).append("@\n").
                    append("@".repeat(80)).append("\n");
            if (MissionServiceImpl.getInstance().findAllMissions().size() == 0) {
                fileWriter.append("No missions have been created so far");
                fileWriter.append("\n@").append("_".repeat(79)).append(System.lineSeparator().repeat(6));
            } else {
                for (FlightMission mission : MissionServiceImpl.getInstance().findAllMissions()) {
                    fileWriter.append("@ NAME: ").append(mission.getName()).append(" | STATUS: ").
                            append(mission.getMissionResult().name());
                    if (!mission.getMissionResult().equals(MissionResult.FAILED)) {
                        fileWriter.append(" | SPACESHIP: ").
                                append(mission.getAssignedSpaceship().getName()).append('\n');
                        fileWriter.append("@ CREW: ");
                        int prettyCrewOutput = 5;
                        for (CrewMember crewMember : mission.getAssignedCrew()) {
                            fileWriter.append(crewMember.getName()).append(',');
                            prettyCrewOutput--;
                            if (prettyCrewOutput == 0) {
                                fileWriter.append("\n@ ");
                                prettyCrewOutput = 5;
                            }
                        }
                    }
                    fileWriter.append("\n@ START: ").append(mission.getFrom().getName()).
                            append(" | FINISH: ").append(mission.getTo().getName()).
                            append(" | START DATE: ").append(String.valueOf(mission.getStartDate())).
                            append(" | END DATE: ").append(String.valueOf(mission.getEndDate())).
                            append("\n@").append("_".repeat(79)).append("\n");
                }
                fileWriter.append(System.lineSeparator().repeat(5));
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void upgradeMissions() {
        MissionServiceImpl.getInstance().updateAllMissions();
    }
}
