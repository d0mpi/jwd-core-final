package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
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
    public ApplicationContext getApplicationContext() {
        log.info("Main menu open");
        return null;
    }

    @Override
    public void printAvailableOptions() {
    }

    @Override
    public void handleUserInput(Short option) {
    }
}
