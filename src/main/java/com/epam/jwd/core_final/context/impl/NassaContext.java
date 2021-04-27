package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.impl.CrewReadStream;
import com.epam.jwd.core_final.util.impl.PlanetReadStream;
import com.epam.jwd.core_final.util.impl.SpaceshipReadStream;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

// todo
@Slf4j
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final ArrayList<Planet> planetMap = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

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
        if(tClass.getName().equals(CrewMember.class.getName())){
            return (Collection<T>) crewMembers;
        } else if (tClass.getName().equals(Spaceship.class.getName())) {
            return (Collection<T>) spaceships;
        } else if (tClass.getName().equals(Planet.class.getName())) {
            return (Collection<T>) planetMap;}
        else if (tClass.getName().equals(FlightMission.class.getName())) {
            return (Collection<T>) flightMissions;
        } else {
            return null;
        }
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        CrewReadStream.INSTANCE.readData();
        SpaceshipReadStream.INSTANCE.readData();
        PlanetReadStream.INSTANCE.readData();
        log.info("Data has been reading successfully");
    }
}
