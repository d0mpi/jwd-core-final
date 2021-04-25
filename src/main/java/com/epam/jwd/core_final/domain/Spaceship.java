package com.epam.jwd.core_final.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role,Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Spaceship extends AbstractBaseEntity {
    @Getter
    @Setter
    private Map<Role, Short> crew;
    @Getter
    @Setter
    private Long flightDistance;
    @Getter
    @Setter
    private Boolean isReadyForNextMission;

    public Spaceship(String name, Map<Role, Short> crew, Long flightDistance) {
        super(name);
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.isReadyForNextMission = true;
    }
}
