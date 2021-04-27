package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.util.OutputTemplates;
import com.epam.jwd.core_final.util.impl.MissionWriteStream;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ApplicationMissionMenu implements ApplicationMenu {
    private static class SingletonHolder {
        private static final ApplicationMissionMenu instance = new ApplicationMissionMenu();
    }

    public static ApplicationMissionMenu getInstance() {
        return ApplicationMissionMenu.SingletonHolder.instance;
    }

    private ApplicationMissionMenu() {
    }

    @Override
    public void getApplicationContext() {
        clearConsole();
        printAvailableOptions();
        readMenuOptionInput();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.MISSION_MENU.getText());
    }

    @Override
    public void readMenuOptionInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Choose menu option (1-5):");
                handleUserInput(Short.parseShort(scanner.nextLine()));
                break;
            } catch (Exception e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                generateRandomMission();
                printAvailableOptions();
                readMenuOptionInput();
            case 2:
                clearConsole();
                showAllMissions();
                printAvailableOptions();
                readMenuOptionInput();
            case 3:
                clearConsole();
                writeAllFlightMissions();
                printAvailableOptions();
                readMenuOptionInput();
            case 4:
                System.out.println("Coming soon...");
                readMenuOptionInput();
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput();
        }
    }

    private void generateRandomMission() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generating new mission...");
        System.out.println("Enter unique mission name:");
        String name = scanner.nextLine();

        Planet planetFrom = PlanetServiceImpl.getInstance().getRandomPlanet();
        Planet planetTo = PlanetServiceImpl.getInstance().getRandomPlanet();
        while (planetTo.equals(planetFrom)) {
            planetTo = PlanetServiceImpl.getInstance().getRandomPlanet();
        }

        Long distance = PlanetServiceImpl.getInstance().getDistanceBetweenPlanets(planetFrom, planetTo);

        Spaceship spaceship = SpaceshipServiceImpl.getInstance().findSpaceshipByCriteria(
                SpaceshipCriteria.builder().isReadyForNextMission(true).minFlightDistance(distance).build()).orElse(null);

        FlightMission flightMission = FlightMissionFactory.getInstance().create(
                name, LocalDate.now(), LocalDate.now(), distance, planetFrom, planetTo);

        //fail mission no spaceship
        if (spaceship == null) {
            System.out.println("There is no spaceship suitable for the mission");
            flightMission.setMissionResult(MissionResult.FAILED);
            try {
                MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
            } catch (DuplicateEntityNameException e) {
                System.out.println("Duplicate mission");
            }
            return;
        }
        ArrayList<CrewMember> crewMembers = findRightCrewMembers(spaceship);
        if (crewMembers == null) {
            System.out.println("There is no crew members suitable for the mission");
            flightMission.setMissionResult(MissionResult.FAILED);
            try {
                MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
            } catch (DuplicateEntityNameException e) {
                System.out.println("Duplicate mission");
            }
            return;
        }

        spaceship.setIsReadyForNextMission(false);
        try {
            SpaceshipServiceImpl.getInstance().updateSpaceshipDetails(spaceship);
        } catch (DuplicateEntityNameException e) {
            System.out.println("Duplicate spaceship");
        }
        flightMission.setAssignedSpaceship(spaceship);
        for(CrewMember crewMember : crewMembers) {
            try {
                crewMember.setIsReadyForNextMission(false);
                CrewServiceImpl.getInstance().updateCrewMemberDetails(crewMember);
            } catch (DuplicateEntityNameException e) {
                System.out.println("Duplicate crew member");
            }
        }
        flightMission.setAssignedCrew(crewMembers);
        try {
            MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
        } catch (DuplicateEntityNameException e) {
            System.out.println("Duplicate mission");
        }

        clearConsole();
        System.out.println(flightMission + " was created successfully\n");
    }

    private ArrayList<CrewMember> findRightCrewMembers(Spaceship spaceship) {
        ArrayList<CrewMember> crewMembers = new ArrayList<>();
        for (Map.Entry<Role, Short> entry : spaceship.getCrew().entrySet()) {
            short numOfRoles = entry.getValue();
            while (numOfRoles-- > 0) {
                CrewMember crewMember = CrewServiceImpl.getInstance().findCrewMemberByCriteria(CrewMemberCriteria.builder().
                        role(entry.getKey()).isReadyForNextMission(true).build()).orElse(null);
                if (crewMember == null) {
                    return null;
                }
                crewMembers.add(crewMember);
            }
        }
        return crewMembers;
    }

    private void writeAllFlightMissions() {
        try {
            MissionWriteStream.getInstance().writeMissionsResult(MissionServiceImpl.getInstance().findAllMissions());
        } catch (IOException e) {
            System.out.println("Error during writing to file");
        }
    }

    private void showAllMissions() {
        LinkedList<FlightMission> flightMissions = new LinkedList<>(MissionServiceImpl.getInstance().findAllMissions());
        if (flightMissions.size() != 0) {
            for (FlightMission flightMission : flightMissions) {
                System.out.println(flightMission.toString());
            }
        } else {
            System.out.println("No mission has been created yet");
        }
        System.out.println();
    }
}
