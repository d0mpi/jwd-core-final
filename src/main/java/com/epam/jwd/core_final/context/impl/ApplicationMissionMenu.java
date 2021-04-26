package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.iostream.OutputTemplates;

import java.util.Scanner;

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
        waitAndReadUserInput();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.MISSION_MENU.getText());
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
                System.out.println(1);
                waitAndReadUserInput();
            case 2:
                System.out.println(2);
                waitAndReadUserInput();
            case 3:
                System.out.println(3);
                waitAndReadUserInput();
            case 4:
                System.out.println(4);
                waitAndReadUserInput();
            case 5:
                System.out.println(4);
                waitAndReadUserInput();
            case 6:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                waitAndReadUserInput();
        }
    }
}
