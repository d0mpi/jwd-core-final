package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.service.MissionService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MissionServiceImpl implements MissionService {
    private final Collection<FlightMission> flightMissions = NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);

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
        return (List<FlightMission>) flightMissions;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return buildFilter((FlightMissionCriteria) criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return buildFilter((FlightMissionCriteria) criteria).findFirst();
    }

    @Override
    public FlightMission updateFlightMissionDetails(FlightMission flightMission) throws DuplicateEntityNameException {
        FlightMission flightMissionFromCollection = findMissionByCriteria(FlightMissionCriteria.builder()
                .name(flightMission.getName()).build()).orElse(null);
        if (flightMissionFromCollection != null) {
            deleteFlightMission(flightMission.getName());
        }
        createMission(flightMission);
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws DuplicateEntityNameException {
        FlightMission duplicateFlightMission = flightMissions.stream().
                filter(mission -> mission.getName().equalsIgnoreCase(flightMission.getName())).findFirst().orElse(null);
        if (duplicateFlightMission != null) {
            throw new DuplicateEntityNameException(flightMission.getName());
        } else {
            flightMissions.add(flightMission);
            return flightMission;
        }
    }

    public void deleteFlightMission(String name){
        FlightMission flightMission = findMissionByCriteria(FlightMissionCriteria.builder().name(name).build()).orElse(null);
        if(flightMission == null) {
            log.info("Mission with this name does not exists");
        } else {
            flightMissions.remove(flightMission);
        }
    }

    private Stream<FlightMission> buildFilter(FlightMissionCriteria flightMissionCriteria) {
        Stream<FlightMission> stream = flightMissions.stream();
        if (flightMissionCriteria.getId() != null) {
            stream = stream.filter(flightMission -> flightMission.getId().equals(flightMissionCriteria.getId()));
        }
        if (flightMissionCriteria.getName() != null) {
            stream = stream.filter(flightMission -> flightMission.getName()
                    .equalsIgnoreCase(flightMissionCriteria.getName()));
        }
        if (flightMissionCriteria.getStartDate() != null) {
            stream = stream.filter(flightMission -> flightMission.getStartDate().equals(flightMissionCriteria.getStartDate()));
        }
        if (flightMissionCriteria.getEndDate() != null) {
            stream = stream.filter(flightMission -> flightMission.getEndDate().equals(flightMissionCriteria.getEndDate()));
        }
        if (flightMissionCriteria.getDistance() != null) {
            stream = stream.filter(flightMission -> flightMission.getDistance().equals(flightMissionCriteria.getDistance()));
        }
        if (flightMissionCriteria.getAssignedCrew() != null) {
            stream = stream.filter(flightMission -> flightMission.getAssignedCrew().equals(flightMissionCriteria.getAssignedCrew()));
        }
        if (flightMissionCriteria.getAssignedSpaceship() != null) {
            stream = stream.filter(flightMission -> flightMission.getAssignedSpaceship().equals(flightMissionCriteria.getAssignedSpaceship()));
        }
        if (flightMissionCriteria.getMissionResult() != null) {
            stream = stream.filter(flightMission -> flightMission.getMissionResult().equals(flightMissionCriteria.getMissionResult()));
        }
        if (flightMissionCriteria.getFrom() != null) {
            stream = stream.filter(flightMission -> flightMission.getFrom().equals(flightMissionCriteria.getFrom()));
        }
        if (flightMissionCriteria.getTo() != null) {
            stream = stream.filter(flightMission -> flightMission.getTo().equals(flightMissionCriteria.getTo()));
        }

        return stream;
    }
}
