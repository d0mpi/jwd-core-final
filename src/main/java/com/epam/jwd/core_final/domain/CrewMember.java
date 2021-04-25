package com.epam.jwd.core_final.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrewMember extends AbstractBaseEntity {
    @Getter
    @Setter
    private Role role;
    @Getter
    @Setter
    private Rank rank;
    @Getter
    @Setter
    private Boolean isReadyForNextMission;

    public CrewMember(String name, Role role, Rank rank) {
        super(name);
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMission = true;
    }
}
