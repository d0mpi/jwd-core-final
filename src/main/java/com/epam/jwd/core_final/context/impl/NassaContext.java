package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.util.iostreamImpl.CrewReadFileStream;
import com.epam.jwd.core_final.util.iostreamImpl.PlanetReadFileStream;
import com.epam.jwd.core_final.util.iostreamImpl.SpaceshipReadFileStream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

// todo
@Slf4j
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new LinkedList<>();
    private final Collection<Spaceship> spaceships = new LinkedList<>();
    private final Collection<Planet> planetMap = new LinkedList<>();
    private final Collection<FlightMission> flightMissions = new LinkedList<>();
    @Setter
    @Getter
    private LocalDate currentDate;

    private static class SingletonHolder {
        private static final NassaContext instance = new NassaContext();
    }

    public static NassaContext getInstance() {
        return NassaContext.SingletonHolder.instance;
    }

    private NassaContext() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (tClass.getName().equals(CrewMember.class.getName())) {
            return (Collection<T>) crewMembers;
        } else if (tClass.getName().equals(Spaceship.class.getName())) {
            return (Collection<T>) spaceships;
        } else if (tClass.getName().equals(Planet.class.getName())) {
            return (Collection<T>) planetMap;
        } else if (tClass.getName().equals(FlightMission.class.getName())) {
            return (Collection<T>) flightMissions;
        } else {
            return null;
        }
    }

    /**
     * You have to read input files, populate collections
     */
    @Override
    public void init() {
        CrewReadFileStream.getInstance().readData();
        SpaceshipReadFileStream.getInstance().readData();
        PlanetReadFileStream.getInstance().readData();
        setCurrentDate(LocalDate.now());
        log.info("Data has been reading successfully");
    }
}
