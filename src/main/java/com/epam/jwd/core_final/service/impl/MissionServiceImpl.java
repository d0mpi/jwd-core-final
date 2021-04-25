package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.service.MissionService;

import java.util.List;
import java.util.Optional;

public class MissionServiceImpl implements MissionService {
    private final NassaContext nassaContext = NassaContext.getInstance();

    private static class SingletonHolder {
        private static final MissionServiceImpl instance = new MissionServiceImpl();
    }

    public static MissionServiceImpl getInstance() {
        return MissionServiceImpl.SingletonHolder.instance;
    }

    private MissionServiceImpl() {
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return null;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return null;
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return Optional.empty();
    }

    @Override
    public FlightMission updateFlightMissionDetails(FlightMission flightMission) {
        return null;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws DuplicateEntityNameException {
        FlightMission duplicateFlightMission = nassaContext.retrieveBaseEntityList(FlightMission.class).stream().
                filter(mission -> mission.getName().equalsIgnoreCase(flightMission.getName())).findFirst().orElse(null);
        if(duplicateFlightMission != null){
            throw new DuplicateEntityNameException(flightMission.getName());
        } else {
            nassaContext.retrieveBaseEntityList(FlightMission.class).add(flightMission);
            return flightMission;
        }
    }
}
