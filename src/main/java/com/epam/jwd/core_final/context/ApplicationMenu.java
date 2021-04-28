package com.epam.jwd.core_final.context;

import java.io.IOException;
import java.util.Scanner;

// todo replace Object with your own types
public interface ApplicationMenu {

    void getApplicationContext();
    void printAvailableOptions();
    default void readMenuOptionInput(short optionNum){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Choose menu option (1-" + optionNum + "):");
                handleUserInput(Short.parseShort(scanner.nextLine()));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void handleUserInput(Short option) throws IOException, InterruptedException;
    
    default void clearConsole(){
        System.out.println(System.lineSeparator().repeat(50));
    }
}
