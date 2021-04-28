package com.epam.jwd.core_final.util.runnableImpl;

import com.epam.jwd.core_final.context.impl.ApplicationMissionMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleTimetableRunnable implements Runnable {

    private static class SingletonHolder {
        private static final ConsoleTimetableRunnable instance = new ConsoleTimetableRunnable();
    }

    public static ConsoleTimetableRunnable getInstance() {
        return ConsoleTimetableRunnable.SingletonHolder.instance;
    }

    private ConsoleTimetableRunnable() {
    }

    @Override
    public void run() {
        log.info("ConsoleTimetableRunnable is running");
        upgradeMissions();
        ApplicationMissionMenu.getInstance().clearConsole();
        System.out.print("@".repeat(80) + "\n");
        System.out.print("@" + " ".repeat(78) + "@\n");
        System.out.print("@ Earth time: ");
        System.out.print(NassaContext.getInstance().getCurrentDate());
        System.out.print("                                                       @\n");
        System.out.print("@" + " ".repeat(78) + "@\n");
        System.out.print("@".repeat(80) + "\n");
        if (MissionServiceImpl.getInstance().findAllMissions().size() == 0) {
            System.out.print("No missions have been created so far");
            System.out.print("\n@" + "_".repeat(79) + "\n");
        } else {
            for (FlightMission mission : MissionServiceImpl.getInstance().findAllMissions()) {
                System.out.print("@ NAME: ");
                System.out.print(mission.getName());
                System.out.print(" | STATUS: ");
                System.out.print(mission.getMissionResult().name());
                if (!mission.getMissionResult().equals(MissionResult.FAILED)) {
                    System.out.print(" | SPACESHIP: ");
                    System.out.print(mission.getAssignedSpaceship().getName());
                    System.out.println();
                    System.out.print("@ CREW: ");
                    int prettyCrewOutput = 5;
                    for (CrewMember crewMember : mission.getAssignedCrew()) {
                        System.out.print(crewMember.getName());
                        System.out.print(',');
                        prettyCrewOutput--;
                        if (prettyCrewOutput == 0) {
                            System.out.print("\n@ ");
                            prettyCrewOutput = 5;
                        }
                    }
                }
                System.out.print("\n@ START DATE: " + mission.getStartDate());
                System.out.print(" | END DATE: " + mission.getEndDate());
                System.out.print(" | START: ");
                System.out.print(mission.getFrom().getName());
                System.out.print(" | FINNISH: ");
                System.out.print(mission.getTo().getName());
                System.out.print("\n@" + "_".repeat(79) + "\n");
            }
        }
        System.out.println("Enter something to return to mission menu: ");
    }

    private void upgradeMissions() {
        MissionServiceImpl.getInstance().updateAllMissions();
    }
}

