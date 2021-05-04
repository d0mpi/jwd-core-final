package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.context.impl.ApplicationSpaceshipMenu;
import com.epam.jwd.core_final.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PlanetReadConsoleStream {
    private static class SingletonHolder {
        private static final PlanetReadConsoleStream instance = new PlanetReadConsoleStream();
    }

    public static PlanetReadConsoleStream getInstance() {
        return PlanetReadConsoleStream.SingletonHolder.instance;
    }

    private PlanetReadConsoleStream() {
    }

    public int readCoordinate(char name) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter " + name + " planet coordinate:");
            int coordinate;
            try {
                coordinate = Integer.parseInt(scanner.nextLine());
                if (coordinate > 0) {
                    return coordinate;
                } else {
                    System.out.println("Invalid value was entered. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    public long readFlightDistance() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter flight distance:");
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    public Map<Role, Short> readCrewMap() {
        Scanner scanner = new Scanner(System.in);
        Map<Role, Short> crew = new HashMap<>();
        int countOfRoles = Role.values().length;
        while (countOfRoles > 0) {
            System.out.println("Enter the number of " + Role.resolveRoleById(countOfRoles) + " in the crew:");
            try {
                crew.put(Role.resolveRoleById(countOfRoles), Short.parseShort(scanner.nextLine()));
                countOfRoles--;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
        return crew;
    }
}
