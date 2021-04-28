package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import lombok.extern.slf4j.Slf4j;

// do the same for other entities
@Slf4j
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    private static class SingletonHolder {
        private static final CrewMemberFactory instance = new CrewMemberFactory();
    }

    public static CrewMemberFactory getInstance() {
        return CrewMemberFactory.SingletonHolder.instance;
    }

    private CrewMemberFactory() {
    }

    @Override
    public CrewMember create(Object... args) throws DuplicateEntityNameException {
        CrewMember crewMember;
        try {
            crewMember = new CrewMember(String.valueOf(args[0]),
                    Role.resolveRoleById(Integer.parseInt(String.valueOf(args[1]))),
                    Rank.resolveRankById(Integer.parseInt(String.valueOf(args[2]))));
            CrewServiceImpl.getInstance().createCrewMember(crewMember);
            log.info("New crew member " + crewMember + " was created successfully");
            return crewMember;
        } catch (NumberFormatException | UnknownEntityException e) {
            log.error("Error during creating person. Check args");
            e.printStackTrace();
        }
        return null;
    }
}
