package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.exception.NotReadyEntityException;
import com.epam.jwd.core_final.service.CrewService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrewServiceImpl implements CrewService {
    private final Collection<CrewMember> crewMembers = NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);

    private static class SingletonHolder {
        private static final CrewServiceImpl instance = new CrewServiceImpl();
    }

    public static CrewServiceImpl getInstance() {
        return CrewServiceImpl.SingletonHolder.instance;
    }

    private CrewServiceImpl() {
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>) crewMembers;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        return buildFilter((CrewMemberCriteria)criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return buildFilter((CrewMemberCriteria) criteria).findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) throws DuplicateEntityNameException {
        CrewMember crewMemberFromCollection = findCrewMemberByCriteria(CrewMemberCriteria.builder()
                .name(crewMember.getName()).build()).orElse(null);
        if (crewMemberFromCollection == null) {
            createCrewMember(crewMember);
        } else {
            crewMemberFromCollection.setRank(crewMember.getRank());
            crewMemberFromCollection.setRole(crewMember.getRole());
            crewMemberFromCollection.setIsReadyForNextMission(crewMember.getIsReadyForNextMission());
        }
        return crewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws NotReadyEntityException {
        if(crewMember.getIsReadyForNextMission()) {
            crewMembers.stream().
            filter(crewMember1 -> crewMember.getName().equals(crewMember1.getName())).
            forEach(crewMember1 -> crewMember1.setIsReadyForNextMission(false));
        } else {
            throw new NotReadyEntityException(crewMember.getName());
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws DuplicateEntityNameException {
        CrewMember duplicateCrewMember = crewMembers.stream().
                filter(member -> member.getName().equalsIgnoreCase(crewMember.getName())).findFirst().orElse(null);
        if(duplicateCrewMember != null){
            throw new DuplicateEntityNameException(crewMember.getName());
        } else {
            crewMembers.add(crewMember);
        }
        return crewMember;
    }

    private Stream<CrewMember> buildFilter(CrewMemberCriteria crewMemberCriteria) {
        Stream<CrewMember> stream = crewMembers.stream();
        if (crewMemberCriteria.getId() != null) {
            stream = stream.filter(crewMember -> crewMember.getId().equals(crewMemberCriteria.getId()));
        }
        if (crewMemberCriteria.getName() != null) {
            stream = stream.filter(crewMember -> crewMember.getName()
                    .equalsIgnoreCase(crewMemberCriteria.getName()));
        }
        if (crewMemberCriteria.getRole() != null) {
            stream = stream.filter(crewMember -> crewMember.getRole() == crewMemberCriteria.getRole());
        }
        if (crewMemberCriteria.getRank() != null) {
            stream = stream.filter(crewMember -> crewMember.getRank() == crewMemberCriteria.getRank());
        }
        if (crewMemberCriteria.getIsReadyForNextMission() != null) {
            stream = stream.filter(crewMember -> crewMember.getIsReadyForNextMission()
                    .equals(crewMemberCriteria.getIsReadyForNextMission()));
        }
        return stream;
    }
}
