package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.util.WriteStream;

import java.util.List;

public class MissionWriteConsoleStream implements WriteStream<FlightMission> {
    private static class SingletonHolder {

        private static final MissionWriteConsoleStream instance = new MissionWriteConsoleStream();
    }

    public static MissionWriteConsoleStream getInstance() {
        return MissionWriteConsoleStream.SingletonHolder.instance;
    }

    private MissionWriteConsoleStream() {
    }

    @Override
    public void writeData(List<FlightMission> flightMissions) {
        if (flightMissions.size() != 0) {
            for (FlightMission flightMission : flightMissions) {
                System.out.println(flightMission.toString());
            }
        } else {
            System.out.println("No mission have been created yet");
        }
        System.out.println();
    }
}
