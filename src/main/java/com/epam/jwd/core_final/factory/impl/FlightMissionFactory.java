package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class FlightMissionFactory implements EntityFactory<FlightMission> {
    private static class SingletonHolder {
        private static final FlightMissionFactory instance = new FlightMissionFactory();
    }

    public static FlightMissionFactory getInstance() {
        return FlightMissionFactory.SingletonHolder.instance;
    }

    private FlightMissionFactory() {
    }

    @Override
    public FlightMission create(Object... args) throws DuplicateEntityNameException {
        FlightMission flightMission;
        try {
            flightMission = new FlightMission(String.valueOf(args[0]),
                    (LocalDate) args[1], (LocalDate) args[2],
                    Long.parseLong(String.valueOf(args[3])),
                    (Planet) args[4], (Planet) args[5]
            );
            flightMission.setMissionResult(MissionResult.PLANNED);
            MissionServiceImpl.getInstance().createMission(flightMission);
            log.info("New mission " + flightMission + " was created successfully");
            return flightMission;
        } catch (NumberFormatException e) {
            log.error("Error during creating new mission. Check args");
            e.printStackTrace();
        }
        return null;
    }
}
