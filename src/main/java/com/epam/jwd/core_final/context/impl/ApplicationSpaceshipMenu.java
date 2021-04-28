package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.OutputTemplates;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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
        readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.SPACESHIP_MENU.getText());
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createSpaceshipByUser();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
            case 2:
                clearConsole();
                generateRandomSpaceship();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
            case 3:
                clearConsole();
                showAllSpaceships();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
            case 4:
                clearConsole();
                showAvailableSpaceships();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput(OutputTemplates.SPACESHIP_MENU.getOptionNum());
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

        Spaceship spaceship;
        while(true) {
            try {
                spaceship = SpaceshipFactory.getInstance().create(name, crewMap, flightDistance);
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Trying to create duplicate spaceship");
                System.out.println("Spaceship with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(spaceship.toString() + " was created successfully\n");
        log.info("Spaceship was created successfully");
    }

    public long readFlightDistance() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter flight distance:");
            try{
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    public Map<Role, Short> readCrewMap() {
        Scanner scanner = new Scanner(System.in);
        Map<Role, Short> crew = new HashMap<>();
        int countOfRoles = Role.values().length;
        while(countOfRoles > 0) {
            System.out.println("Enter the number of " + Role.resolveRoleById(countOfRoles) + " in the crew:");
            try {
                crew.put(Role.resolveRoleById(countOfRoles), Short.parseShort(scanner.nextLine()));
                countOfRoles--;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
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
            temp = random.nextInt(4);
            crewMap.put(Role.resolveRoleById(i), (short) temp);
            sumCrew += temp;
        }
        if (sumCrew == 0){
            crewMap.put(Role.COMMANDER, (short) 1);
        }

        Spaceship spaceship;
        while (true) {
            try {
                spaceship = SpaceshipFactory.getInstance().create(name, crewMap, flightDistance);
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Trying to generate duplicate spaceship");
                System.out.println("Spaceship with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(spaceship.toString() + " was created successfully\n");
        log.info("Spaceship was generated successfully");
    }
}
