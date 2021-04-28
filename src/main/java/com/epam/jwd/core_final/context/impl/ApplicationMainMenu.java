package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.OutputTemplates;
import lombok.extern.slf4j.Slf4j;

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
        readMenuOptionInput(OutputTemplates.MAIN_MENU.getOptionNum());
    }

    @Override
    public void printAvailableOptions() {
        clearConsole();
        System.out.println(OutputTemplates.MAIN_MENU.getText());
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
                System.out.println("Program terminated. Bye ;)");
                System.exit(0);
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput(OutputTemplates.MAIN_MENU.getOptionNum());
        }
    }
}
