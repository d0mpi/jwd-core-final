package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class FlightMissionCriteria extends Criteria<FlightMission> {
    @Getter
    private final LocalDate startDate;
    @Getter
    private final LocalDate endDate;
    @Getter
    private final Long distance;
    @Getter
    private final Spaceship assignedSpaceship;
    @Getter
    private final List<CrewMember> assignedCrew;
    @Getter
    private final MissionResult missionResult;
    @Getter
    private final Planet from;
    @Getter
    private final Planet to;

    public FlightMissionCriteria(Long id, String name,
                                 LocalDate startDate, LocalDate endDate,
                                 Long distance, Spaceship assignedSpaceship,
                                 List<CrewMember> assignedCrew, MissionResult missionResult,
                                 Planet from, Planet to) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceship = assignedSpaceship;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
        this.from = from;
        this.to = to;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long distance;
        private Spaceship assignedSpaceship;
        private List<CrewMember> assignedCrew;
        private MissionResult missionResult;
        private Planet from;
        private Planet to;

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder startDate(LocalDate startDate){
            this.startDate = startDate;
            return this;
        }
        public Builder endDate(LocalDate endDate){
            this.endDate = endDate;
            return this;
        }
        public Builder distance(Long distance){
            this.distance = distance;
            return this;
        }
        public Builder assignedSpaceship(Spaceship assignedSpaceship){
            this.assignedSpaceship = assignedSpaceship;
            return this;
        }
        public Builder id(List<CrewMember> assignedCrew){
            this.assignedCrew = assignedCrew;
            return this;
        }
        public Builder missionResult(MissionResult missionResult){
            this.missionResult = missionResult;
            return this;
        }
        public Builder from(Planet from){
            this.from = from;
            return this;
        }
        public Builder to(Planet to){
            this.to = to;
            return this;
        }
        public FlightMissionCriteria build(){
            return new FlightMissionCriteria(id, name,
                    startDate, endDate,
                    distance, assignedSpaceship,
                    assignedCrew, missionResult,
                    from, to);
        }
    }
}
