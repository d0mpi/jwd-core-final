package service;


import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class CrewServiceTest {
    private CrewServiceImpl service;

    @Before
    public void init() {
        service = CrewServiceImpl.getInstance();
    }

    @Test
    public void findAllCrewMembersByCriteriaTest() {
        List<CrewMember> collect = service.findAllCrewMembers()
                .stream()
                .filter(e -> e.getRole() == Role.COMMANDER || e.getRank() == Rank.CAPTAIN)
                .collect(Collectors.toList());
        List<CrewMember> allCrewMembersByCriteria = service.findAllCrewMembersByCriteria(
                CrewMemberCriteria.builder().rank(Rank.CAPTAIN).role(Role.PILOT).build());
        Assert.assertEquals(collect.size(), allCrewMembersByCriteria.size());
    }

    @Test
    public void createCrewMemberTest() throws DuplicateEntityNameException {
        CrewMember crewMember = new CrewMember("Mikl Mikl", Role.PILOT, Rank.SECOND_OFFICER);
        service.createCrewMember(crewMember);
        CrewMember crewMemberByCriteria = service.findCrewMemberByCriteria(
                CrewMemberCriteria.builder().name("Mikl Mikl").build()).orElse(null);
        Assert.assertEquals(crewMemberByCriteria,crewMember);
    }

    @Test(expected = DuplicateEntityNameException.class)
    public void createCrewMemberExceptionTest() throws DuplicateEntityNameException{
        service.createCrewMember(new CrewMember("Duplicate Name", Role.PILOT, Rank.SECOND_OFFICER));
        service.createCrewMember(new CrewMember("Duplicate Name", Role.COMMANDER, Rank.CAPTAIN));
    }
}
