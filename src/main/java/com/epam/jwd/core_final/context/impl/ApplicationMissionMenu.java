package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.domain.OutputTemplates;
import com.epam.jwd.core_final.util.runnableImpl.ConsoleTimetableRunnable;
import com.epam.jwd.core_final.util.iostreamImpl.MissionsSerializeToFileStream;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class ApplicationMissionMenu implements ApplicationMenu {
    private static class SingletonHolder {
        private static final ApplicationMissionMenu instance = new ApplicationMissionMenu();
    }

    public static ApplicationMissionMenu getInstance() {
        return ApplicationMissionMenu.SingletonHolder.instance;
    }

    private ApplicationMissionMenu() {
    }

    private final ScheduledExecutorService realtimeTimetableToConsole = Executors.newSingleThreadScheduledExecutor();
    private Future<?> scheduledFuture;

    @Override
    public void getApplicationContext() {
        clearConsole();
        printAvailableOptions();
        readMenuOptionInput(OutputTemplates.MISSION_MENU.getOptionNum());
    }

    @Override
    public void printAvailableOptions() {

        System.out.println(OutputTemplates.MISSION_MENU.getText());
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                generateRandomMission();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.MISSION_MENU.getOptionNum());
            case 2:
                clearConsole();
                showAllMissions();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.MISSION_MENU.getOptionNum());
            case 3:
                clearConsole();
                writeAllFlightMissions();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.MISSION_MENU.getOptionNum());
            case 4:
                clearConsole();
                startRealtimeTimetableToConsole();
                waitUserInput();
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput((short) 5);
        }
    }

    private void waitUserInput() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        pauseTimetable();
        this.getApplicationContext();
        System.out.println("Enter something to return to mission menu: ");
    }

    private void startRealtimeTimetableToConsole() {
        scheduledFuture = realtimeTimetableToConsole.scheduleAtFixedRate(ConsoleTimetableRunnable.getInstance(), 0,
                ApplicationProperties.getInstance().getFileRefreshRate(), TimeUnit.SECONDS);
    }

    private void pauseTimetable() {
        scheduledFuture.cancel(true);
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

        Random random = new Random();
        LocalDate date = NassaContext.getInstance().getCurrentDate();
        FlightMission flightMission;
        while (true) {
            try {
                flightMission = FlightMissionFactory.getInstance().create(
                        name, date.plusDays(random.nextInt(20)), date.plusDays(random.nextInt(200) + 21), distance, planetFrom, planetTo);
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Trying to create duplicate mission");
                System.out.println("Mission with this name already exists. Please enter name again");
                name = scanner.nextLine();
            }
        }

        //fail mission no spaceship
        if (spaceship == null) {
            System.out.println("There is no spaceship suitable for the mission");
            log.error("Mission was not create");
            flightMission.setMissionResult(MissionResult.FAILED);
            MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
            return;
        }

        ArrayList<CrewMember> crewMembers = findRightCrewMembers(spaceship);
        if (crewMembers == null) {
            System.out.println("There is no crew members suitable for the mission");
            log.error("Mission was not create");
            flightMission.setMissionResult(MissionResult.FAILED);
            MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);
            return;
        }

        spaceship.setIsReadyForNextMission(false);
        SpaceshipServiceImpl.getInstance().updateSpaceshipDetails(spaceship);
        flightMission.setAssignedSpaceship(spaceship);
        for (CrewMember crewMember : crewMembers) {
            crewMember.setIsReadyForNextMission(false);
            CrewServiceImpl.getInstance().updateCrewMemberDetails(crewMember);
        }
        flightMission.setAssignedCrew(crewMembers);
        MissionServiceImpl.getInstance().updateFlightMissionDetails(flightMission);

        clearConsole();
        System.out.println(flightMission + " was created successfully\n");
        log.info("Mission was generated successfully");
    }

    private ArrayList<CrewMember> findRightCrewMembers(Spaceship spaceship) {
        ArrayList<CrewMember> resultCrew = new ArrayList<>();
        for (Map.Entry<Role, Short> entry : spaceship.getCrew().entrySet()) {
            ArrayList<CrewMember> rightCrewMember = new ArrayList<>(CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(CrewMemberCriteria.builder().
                    role(entry.getKey()).isReadyForNextMission(true).build()));
            if (rightCrewMember.size() < entry.getValue()) {
                return null;
            }
            resultCrew.addAll(rightCrewMember.stream().limit(entry.getValue()).collect(Collectors.toList()));
        }
        return resultCrew;
    }

    private void writeAllFlightMissions() {
        try {
            MissionsSerializeToFileStream.getInstance().writeMissionsResult(MissionServiceImpl.getInstance().findAllMissions());
        } catch (IOException e) {
            log.error("Error during writing to file");
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
