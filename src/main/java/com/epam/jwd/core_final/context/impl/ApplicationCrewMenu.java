package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.OutputTemplates;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

@Slf4j
public class ApplicationCrewMenu implements ApplicationMenu {
    private static class SingletonHolder {
        private static final ApplicationCrewMenu instance = new ApplicationCrewMenu();
    }

    public static ApplicationCrewMenu getInstance() {
        return ApplicationCrewMenu.SingletonHolder.instance;
    }

    private ApplicationCrewMenu() {
    }

    @Override
    public void getApplicationContext() {
        clearConsole();
        printAvailableOptions();
        readMenuOptionInput();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.CREW_MEMBER_MENU.getText());
    }

    @Override
    public void readMenuOptionInput() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Choose menu option (1-5):");
                handleUserInput(Short.parseShort(scanner.nextLine()));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createCrewMemberByUser();
                printAvailableOptions();
                readMenuOptionInput();
            case 2:
                clearConsole();
                generateRandomCrewMember();
                printAvailableOptions();
                readMenuOptionInput();
            case 3:
                clearConsole();
                showAllCrewMembers();
                printAvailableOptions();
                readMenuOptionInput();
            case 4:
                clearConsole();
                showAvailableCrewMembers();
                printAvailableOptions();
                readMenuOptionInput();
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput();
        }
    }

    public void showAllCrewMembers() {
        LinkedList<CrewMember> crewMembers = new LinkedList<>(CrewServiceImpl.getInstance().findAllCrewMembers());
        for (CrewMember crewMember : crewMembers) {
            System.out.println(crewMember.toString());
        }
        System.out.println();
    }

    public void showAvailableCrewMembers() {
        LinkedList<CrewMember> crewMembers = new LinkedList<>(CrewServiceImpl.getInstance().
                findAllCrewMembersByCriteria(CrewMemberCriteria.builder().isReadyForNextMission(true).build()));
        if (crewMembers.size() != 0) {
            for (CrewMember crewMember : crewMembers) {
                System.out.println(crewMember.toString());
            }
        } else {
            System.out.println("No crew members has been created yet");
        }
        System.out.println();
    }

    public void createCrewMemberByUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creating new crew member...");
        System.out.println("Enter unique name:");
        String name = scanner.nextLine();
        int role = readRoleId(), rank = readRankId();

        CrewMember crewMember;
        while (true) {
            try {
                crewMember = CrewMemberFactory.getInstance().create(name, role, rank);
                break;
            } catch (DuplicateEntityNameException e) {
                log.info("Create duplicate person");
                System.out.println("Crew member with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(crewMember.toString() + " was created successfully\n");
    }

    public int readRoleId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(OutputTemplates.ROLE_LIST.getText());
        while (true) {
            System.out.println("Choose role id (1-" + Role.values().length + "):");
            short id;
            try {
                id = Short.parseShort(scanner.nextLine());
                if (id <= Role.values().length && id >= 1) {
                    return id;
                } else {
                    System.out.println("Invalid value was entered. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }
        }
    }

    public int readRankId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(OutputTemplates.RANK_LIST.getText());
        while (true) {
            System.out.println("Choose rank id (1-" + Rank.values().length + "):");
            short id;
            try {
                id = Short.parseShort(scanner.nextLine());
                if (id <= Rank.values().length && id >= 1) {
                    return id;
                } else {
                    System.out.println("Invalid value was entered. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid value was entered. Please try again.");
            }


        }
    }

    public void generateRandomCrewMember() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generating new crew member...");
        System.out.println("Enter unique name:");
        String name = scanner.nextLine();
        int role = random.nextInt(Role.values().length - 1) + 1,
                rank = random.nextInt(Rank.values().length - 1) + 1;
        CrewMember crewMember;
        while (true) {
            try {
                crewMember = CrewMemberFactory.getInstance().create(name, role, rank);
                break;
            } catch (DuplicateEntityNameException e) {
                log.info("Generate duplicate crew member");
                System.out.println("Crew member with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(crewMember.toString() + " was created successfully\n");
    }
}
