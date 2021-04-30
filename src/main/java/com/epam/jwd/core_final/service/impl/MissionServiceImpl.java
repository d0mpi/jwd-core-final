package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.service.MissionService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MissionServiceImpl implements MissionService {
    private final Collection<FlightMission> flightMissions = NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class);

    public void updateAllMissions() {
        LinkedList<FlightMission> flightMissions = (LinkedList<FlightMission>) findAllMissions();
        LocalDate localDate = NassaContext.getInstance().getCurrentDate();
        for (FlightMission flightMission : flightMissions) {
            if (localDate.isAfter(flightMission.getStartDate()) &&
                    localDate.isBefore(flightMission.getEndDate()) &&
                    !flightMission.getMissionResult().equals(MissionResult.FAILED)) {
                flightMission.setMissionResult(MissionResult.IN_PROGRESS);
            } else if (localDate.isAfter(flightMission.getEndDate()) &&
                    !flightMission.getMissionResult().equals(MissionResult.FAILED) &&
                    !flightMission.getMissionResult().equals(MissionResult.COMPLETED)) {
                flightMission.setMissionResult(MissionResult.COMPLETED);
                Spaceship spaceship = flightMission.getAssignedSpaceship();
                spaceship.setIsReadyForNextMission(true);
                SpaceshipServiceImpl.getInstance().updateSpaceshipDetails(spaceship);
                for (CrewMember crewMember : flightMission.getAssignedCrew()) {
                    crewMember.setIsReadyForNextMission(true);
                    CrewServiceImpl.getInstance().updateCrewMemberDetails(crewMember);
                }
            }
            MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
        }
    }

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
    public void updateFlightMissionDetails(FlightMission flightMission) {
        FlightMission flightMissionFromCollection = findMissionByCriteria(FlightMissionCriteria.builder()
                .name(flightMission.getName()).build()).orElse(null);
        if (flightMissionFromCollection == null) {
            try {
                createMission(flightMission);
            } catch (DuplicateEntityNameException ignored) {
            }
        } else {
            flightMissionFromCollection.setMissionResult(flightMission.getMissionResult());
            flightMissionFromCollection.setFrom(flightMission.getFrom());
            flightMissionFromCollection.setTo(flightMission.getTo());
            flightMissionFromCollection.setAssignedCrew(flightMission.getAssignedCrew());
            flightMissionFromCollection.setDistance(flightMissionFromCollection.getDistance());
            flightMissionFromCollection.setEndDate(flightMission.getEndDate());
            flightMissionFromCollection.setStartDate(flightMission.getStartDate());
            flightMissionFromCollection.setAssignedSpaceship(flightMission.getAssignedSpaceship());
        }
    }

    @Override
    public void createMission(FlightMission flightMission) throws DuplicateEntityNameException {
        FlightMission duplicateFlightMission = flightMissions.stream().
                filter(mission -> mission.getName().equalsIgnoreCase(flightMission.getName())).findFirst().orElse(null);

        if (duplicateFlightMission != null) {
            throw new DuplicateEntityNameException(flightMission.getName());
        } else {

            flightMissions.add(flightMission);
        }
        log.info("New mission was added to collection");
    }

    public void deleteFlightMission(String name) {
        FlightMission flightMission = findMissionByCriteria(FlightMissionCriteria.builder().name(name).build()).orElse(null);
        if (flightMission == null) {
            log.info("Mission with this name does not exists");
        } else {
            NassaContext.getInstance().retrieveBaseEntityList(FlightMission.class).remove(flightMission);
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
