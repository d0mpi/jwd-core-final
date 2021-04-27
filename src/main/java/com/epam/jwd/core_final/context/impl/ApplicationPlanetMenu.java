package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.util.OutputTemplates;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Scanner;

@Slf4j
public class ApplicationPlanetMenu implements ApplicationMenu {
    private static class SingletonHolder {
        private static final ApplicationPlanetMenu instance = new ApplicationPlanetMenu();
    }

    public static ApplicationPlanetMenu getInstance() {
        return ApplicationPlanetMenu.SingletonHolder.instance;
    }

    private ApplicationPlanetMenu() {
    }

    @Override
    public void getApplicationContext() {
        clearConsole();
        printAvailableOptions();
        readMenuOptionInput();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.PLANET_MENU.getText());
    }

    @Override
    public void readMenuOptionInput() {
        System.out.println("Choose menu option (1-4):");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextShort()) {
            handleUserInput(scanner.nextShort());
        } else {
            System.out.println("Invalid value was entered. Please try again.");
            readMenuOptionInput();
        }
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createPlanet();
                printAvailableOptions();
                readMenuOptionInput();
            case 2:
                clearConsole();
                generatePlanet();
                printAvailableOptions();
                readMenuOptionInput();
            case 3:
                clearConsole();
                showAllPlanets();
                printAvailableOptions();
                readMenuOptionInput();
            case 4:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput();
        }
    }

    private void showAllPlanets() {
        LinkedList<Planet> planets = new LinkedList<>(PlanetServiceImpl.getInstance().findAllPlanets());
        for (Planet planet : planets) {
            System.out.println(planet.toString());
        }
        System.out.println();
    }

    private void generatePlanet() {

    }

    private int readCoordinateX() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter X planet coordinate:");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid value was entered. Please try again.");
            }
        } while (true);
    }

    private int readCoordinateY() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter Y planet coordinate:");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid value was entered. Please try again.");
            }
        } while (true);
    }

    private void createPlanet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creating new planet...");
        System.out.println("Enter unique planet name:");
        String name = scanner.nextLine();
        int xCoordinate = readCoordinateX(), yCoordinate = readCoordinateY();

        Planet planet;
        while (true) {
            try {
                planet = PlanetFactory.getInstance().create(name, new Point(xCoordinate, yCoordinate));
                break;
            } catch (DuplicateEntityNameException e) {
                log.info("Attempt to create duplicate planet");
                System.out.println("Planet with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(planet.toString() + " was created successfully\n");

    }
}
