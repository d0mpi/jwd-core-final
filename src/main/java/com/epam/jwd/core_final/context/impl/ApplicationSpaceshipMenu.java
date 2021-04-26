package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.iostream.OutputTemplates;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ApplicationSpaceshipMenu implements ApplicationMenu {
    private static class SingletonHolder {
        private static final ApplicationSpaceshipMenu instance = new ApplicationSpaceshipMenu();
    }

    public static ApplicationSpaceshipMenu getInstance() {
        return ApplicationSpaceshipMenu.SingletonHolder.instance;
    }

    private ApplicationSpaceshipMenu() {
    }

    @Override
    public void getApplicationContext() {
        clearConsole();
        printAvailableOptions();
        waitAndReadUserInput();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.SPACESHIP_MENU.getText());
    }

    @Override
    public void waitAndReadUserInput() {
        System.out.println("Choose menu option (1-5):");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextShort()) {
            handleUserInput(scanner.nextShort());
        } else {
            System.out.println("Invalid value was entered. Please try again.");
            waitAndReadUserInput();
        }
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createSpaceshipByUser();
                printAvailableOptions();
                waitAndReadUserInput();
            case 2:
                clearConsole();
                generateRandomSpaceship();
                printAvailableOptions();
                waitAndReadUserInput();
            case 3:
                clearConsole();
                showAllSpaceships();
                printAvailableOptions();
                waitAndReadUserInput();
            case 4:
                clearConsole();
                showAvailableSpaceships();
                printAvailableOptions();
                waitAndReadUserInput();
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                waitAndReadUserInput();
        }
    }

    public void showAllSpaceships() {
        LinkedList<Spaceship> spaceships = new LinkedList<>(SpaceshipServiceImpl.getInstance().findAllSpaceships());
        for (Spaceship spaceship : spaceships) {
            System.out.println(spaceship.toString());
        }
        System.out.println();
    }

    public void showAvailableSpaceships() {
        LinkedList<Spaceship> spaceships = new LinkedList<>(SpaceshipServiceImpl.getInstance().
                findAllSpaceshipsByCriteria(SpaceshipCriteria.builder().isReadyForNextMission(true).build()));
        for (Spaceship spaceship : spaceships) {
            System.out.println(spaceship.toString());
        }
        System.out.println();
    }

    public void createSpaceshipByUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creating new spaceship...");
        System.out.println("Enter unique name:");
        String name = scanner.nextLine();
        Long flightDistance = readFlightDistance();
        Map<Role, Short> crewMap = readCrewMap();

        Spaceship spaceship = null;
        while(true) {
            try {
                spaceship = SpaceshipFactory.getInstance().create(name, crewMap, flightDistance);
                break;
            } catch (DuplicateEntityNameException e) {
                log.info("Create duplicate spaceship");
                System.out.println("Spaceship with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(spaceship.toString() + " was created successfully\n");
    }

    public long readFlightDistance() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter flight distance:");
            if (scanner.hasNextLong()) {
                return scanner.nextLong();
            } else {
                System.out.println("Invalid value was entered. Please try again.");
                waitAndReadUserInput();
            }
        } while (true);
    }

    public Map<Role, Short> readCrewMap() {
        Scanner scanner = new Scanner(System.in);
        Map<Role, Short> crew = new HashMap<>();
        int countOfRoles = Role.values().length;
        do {
            System.out.println("Enter the number of " + Role.resolveRoleById(countOfRoles) + " in the crew:");
            if (scanner.hasNextShort()) {
                crew.put(Role.resolveRoleById(countOfRoles), scanner.nextShort());
                countOfRoles--;
            } else {
                System.out.println("Invalid value was entered. Please try again.");
                waitAndReadUserInput();
            }
        } while (countOfRoles > 0);
        return crew;
    }

    public void generateRandomSpaceship() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generating new spaceship...");
        System.out.println("Enter unique name:");
        String name = scanner.nextLine();

        Long flightDistance = (long) random.nextInt(1000000);
        Map<Role, Short> crewMap = new HashMap<>();
        int sumCrew = 0, temp;
        for (int i = 1; i <= Role.values().length; i++) {
            temp = random.nextInt(5);
            crewMap.put(Role.resolveRoleById(i), (short) temp);
            sumCrew += temp;
        }
        if (sumCrew == 0){
            crewMap.put(Role.COMMANDER, (short) 1);
        }

        Spaceship spaceship = null;
        while (true) {
            try {
                spaceship = SpaceshipFactory.getInstance().create(name, crewMap, flightDistance);
                break;
            } catch (DuplicateEntityNameException e) {
                log.info("Generate duplicate spaceship");
                System.out.println("Spaceship with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(spaceship.toString() + " was created successfully\n");
    }
}
