package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.iostream.OutputTemplates;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ApplicationMainMenu implements ApplicationMenu {

    private static class SingletonHolder {
        private static final ApplicationMainMenu instance = new ApplicationMainMenu();
    }

    public static ApplicationMainMenu getInstance() {
        return ApplicationMainMenu.SingletonHolder.instance;
    }

    private ApplicationMainMenu() {
    }

    @Override
    public void getApplicationContext() {
        printAvailableOptions();
        waitAndReadUserInput();
    }

    @Override
    public void printAvailableOptions() {
        clearConsole();
        System.out.println(OutputTemplates.MAIN_MENU.getText());
    }

    @Override
    public void waitAndReadUserInput() throws IllegalArgumentException {
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
                ApplicationCrewMenu.getInstance().getApplicationContext();
            case 2:
                ApplicationSpaceshipMenu.getInstance().getApplicationContext();
            case 3:
                ApplicationMissionMenu.getInstance().getApplicationContext();
            case 4:
                System.out.println(4);
                waitAndReadUserInput();
            case 5:
                System.out.println("Shutting down the program...");
                System.exit(0);
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                waitAndReadUserInput();
        }
    }
}
