package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.util.OutputTemplates;
import com.epam.jwd.core_final.util.TimerMissionWriter;
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
        readMenuOptionInput();
    }

    @Override
    public void printAvailableOptions() {
        clearConsole();
        System.out.println(OutputTemplates.MAIN_MENU.getText());
    }

    @Override
    public void readMenuOptionInput() throws IllegalArgumentException {
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
                ApplicationCrewMenu.getInstance().getApplicationContext();
            case 2:
                ApplicationSpaceshipMenu.getInstance().getApplicationContext();
            case 3:
                ApplicationMissionMenu.getInstance().getApplicationContext();
            case 4:
                ApplicationPlanetMenu.getInstance().getApplicationContext();
            case 5:
                System.out.println("Shutting down the program...");
                TimerMissionWriter.getInstance().closeWriter();
                System.exit(0);
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput();
        }
    }
}
