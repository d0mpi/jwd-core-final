package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import lombok.Getter;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    @Getter
    private final Map<Role, Short> crew;
    @Getter
    private final Long minFlightDistance;
    @Getter
    private final Boolean isReadyForNextMission;

    public SpaceshipCriteria(Long id,String name, Map<Role, Short> crew, Long flightDistance, Boolean isReadyForNextMission) {
        super(id,name);
        this.crew = crew;
        this.minFlightDistance = flightDistance;
        this.isReadyForNextMission = isReadyForNextMission;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private String name;
        private Map<Role, Short> crew;
        private Long flightDistance;
        private Boolean isReadyForNextMission;

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder crew(Map<Role, Short> crew){
            this.crew = crew;
            return this;
        }
        public Builder minFlightDistance(Long minFlightDistance){
            this.flightDistance = minFlightDistance;
            return this;
        }
        public Builder isReadyForNextMission(Boolean isReadyForNextMission){
            this.isReadyForNextMission = isReadyForNextMission;
            return this;
        }
        public SpaceshipCriteria build(){
            return new SpaceshipCriteria(id,name,crew,flightDistance,isReadyForNextMission);
        }
    }
}
