package com.epam.jwd.core_final.context;

import java.io.IOException;

// todo replace Object with your own types
public interface ApplicationMenu {

    void getApplicationContext();
    void printAvailableOptions();
    void waitAndReadUserInput();
    void handleUserInput(Short option) throws IOException, InterruptedException;
    
    default void clearConsole(){
        System.out.println(System.lineSeparator().repeat(50));
    }
}
