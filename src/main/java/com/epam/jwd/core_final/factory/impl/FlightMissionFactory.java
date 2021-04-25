package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;

import java.time.LocalDate;

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
    public FlightMission create(Object... args) {
        FlightMission flightMission = null;
        try {
            flightMission = new FlightMission(String.valueOf(args[0]), String.valueOf(args[1]),
                    (LocalDate) args[2], (LocalDate) args[3],
                    Long.parseLong(String.valueOf(args[4])),
                    (Planet) args[5], (Planet) args[6]
            );
            MissionServiceImpl.getInstance().createMission(flightMission);
            return flightMission;
        } catch (NumberFormatException | DuplicateEntityNameException e) {
            e.printStackTrace();
        }
        return null;
    }
}
