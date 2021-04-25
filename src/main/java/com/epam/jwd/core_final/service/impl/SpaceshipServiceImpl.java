package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.exception.NotReadyEntityException;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceshipServiceImpl implements SpaceshipService {

    private final Collection<Spaceship> spaceships = NassaContext.getInstance().retrieveBaseEntityList(Spaceship.class);

    private static class SingletonHolder {
        private static final SpaceshipServiceImpl instance = new SpaceshipServiceImpl();
    }

    public static SpaceshipServiceImpl getInstance() {
        return SpaceshipServiceImpl.SingletonHolder.instance;
    }

    private SpaceshipServiceImpl() {
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>) spaceships;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        return buildFilter((SpaceshipCriteria) criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return buildFilter((SpaceshipCriteria) criteria).findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws NotReadyEntityException {
        if(spaceship.getIsReadyForNextMission()) {
            spaceships.stream().
                    filter(spaceship1 -> spaceship.getName().equals(spaceship1.getName())).
                    forEach(spaceship1 -> spaceship1.setIsReadyForNextMission(false));
        } else {
            throw new NotReadyEntityException(spaceship.getName());
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws DuplicateEntityNameException {
        Spaceship duplicateSpaceship = spaceships.stream().
                filter(ship -> ship.getName().equalsIgnoreCase(spaceship.getName())).findFirst().orElse(null);
        if(duplicateSpaceship != null){
            throw new DuplicateEntityNameException(spaceship.getName());
        } else {
            spaceships.add(spaceship);
            return spaceship;
        }
    }

    private Stream<Spaceship> buildFilter(SpaceshipCriteria spaceshipCriteria) {
        Stream<Spaceship> stream = spaceships.stream();
        if (spaceshipCriteria.getId() != null) {
            stream = stream.filter(crewMember -> crewMember.getId().equals(spaceshipCriteria.getId()));
        }
        if (spaceshipCriteria.getName() != null) {
            stream = stream.filter(crewMember -> crewMember.getName()
                    .equalsIgnoreCase(spaceshipCriteria.getName()));
        }
        if (spaceshipCriteria.getIsReadyForNextMission() != null) {
            stream = stream.filter(crewMember -> crewMember.getIsReadyForNextMission()
                    .equals(spaceshipCriteria.getIsReadyForNextMission()));
        }
        if (spaceshipCriteria.getFlightDistance() != null) {
            stream = stream.filter(crewMember -> crewMember.getFlightDistance()
                    .equals(spaceshipCriteria.getFlightDistance()));
        }
        if (spaceshipCriteria.getCrew() != null) {
            stream = stream.filter(crewMember -> crewMember.getCrew()
                    .equals(spaceshipCriteria.getCrew()));
        }
        return stream;
    }
}
