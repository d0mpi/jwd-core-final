package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.OutputTemplates;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;
import com.epam.jwd.core_final.util.iostreamImpl.PlanetReadConsoleStream;
import com.epam.jwd.core_final.util.iostreamImpl.PlanetWriteConsoleStream;
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
        readMenuOptionInput((short) 4);
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.PLANET_MENU.getText());
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createPlanet();
                printAvailableOptions();
                readMenuOptionInput((short) 4);
            case 2:
                clearConsole();
                generatePlanet();
                printAvailableOptions();
                readMenuOptionInput((short) 4);
            case 3:
                clearConsole();
                showAllPlanets();
                printAvailableOptions();
                readMenuOptionInput((short) 4);
            case 4:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput((short) 4);
        }
    }

    private void showAllPlanets() {
        LinkedList<Planet> planets = new LinkedList<>(PlanetServiceImpl.getInstance().findAllPlanets());
        PlanetWriteConsoleStream.getInstance().writeData(planets);
    }

    private void generatePlanet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generating new planet...");
        System.out.println("Enter unique planet name:");
        String name = scanner.nextLine();

        Planet planet;
        while (true) {
            try {
                planet = PlanetFactory.getInstance().create(name,
                        new Point(PlanetServiceImpl.getInstance().generateRandomCoordinate(),
                                PlanetServiceImpl.getInstance().generateRandomCoordinate()));
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Trying to create duplicate planet " + e.getMessage());
                System.out.println("Planet with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(planet.toString() + " was created successfully\n");
        log.info("Planet " + planet + " was generated successfully");
    }

    private void createPlanet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating new planet...");
        System.out.println("Enter unique planet name:");
        String name = scanner.nextLine();

        Planet planet;
        while (true) {
            try {
                planet = PlanetFactory.getInstance().create(name,
                        new Point(PlanetReadConsoleStream.getInstance().readCoordinate('X'),
                                PlanetReadConsoleStream.getInstance().readCoordinate('Y')));
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Attempt to generate duplicate planet");
                System.out.println("Planet with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(planet.toString() + " was created successfully\n");
        log.info("Planet " + planet + " was created successfully");
    }
}
