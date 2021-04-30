package com.epam.jwd.core_final.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */

@EqualsAndHashCode(callSuper = true)
public class FlightMission extends AbstractBaseEntity {
    @Getter
    @Setter
    private LocalDate startDate;
    @Getter
    @Setter
    private LocalDate endDate;
    @Getter
    @Setter
    private Long distance;
    @Getter
    @Setter
    private Spaceship assignedSpaceship = null;
    @Getter
    @Setter
    private List<CrewMember> assignedCrew = null;
    @Getter
    @Setter
    private MissionResult missionResult;
    @Getter
    @Setter
    private Planet from;
    @Getter
    @Setter
    private Planet to;

    public FlightMission(String name,
                         LocalDate startDate, LocalDate endDate,
                         Long distance,
                         Planet from, Planet to) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String text = "FlightMission " + getName() + " id=" + getId() +
                "\nstartDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance;
        if (!missionResult.equals(MissionResult.FAILED) && assignedSpaceship != null && assignedCrew != null) {
            text += ", assignedSpaceship=" + assignedSpaceship +
                    "\nAssignedCrew= {"
                    + assignedCrew;
        }
        text += "\nMissionResult=" + missionResult +
                ", from " + from +
                ", to " + to +
                '}';
        return text;
    }
}
