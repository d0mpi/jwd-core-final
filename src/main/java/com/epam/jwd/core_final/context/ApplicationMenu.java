package com.epam.jwd.core_final.context;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
    }

    default void handleUserInput(Short o) {
    }
}
