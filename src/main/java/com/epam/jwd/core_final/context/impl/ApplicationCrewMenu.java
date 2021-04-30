package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.OutputTemplates;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.util.iostreamImpl.CrewReadFileStream;
import com.epam.jwd.core_final.util.iostreamImpl.CrewWriteConsoleStream;
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
        readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
    }

    @Override
    public void printAvailableOptions() {
        System.out.println(OutputTemplates.CREW_MEMBER_MENU.getText());
    }

    @Override
    public void handleUserInput(Short option) {
        switch (option) {
            case 1:
                clearConsole();
                createCrewMemberByUser();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
            case 2:
                clearConsole();
                generateRandomCrewMember();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
            case 3:
                clearConsole();
                showAllCrewMembers();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
            case 4:
                clearConsole();
                showAvailableCrewMembers();
                printAvailableOptions();
                readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
            case 5:
                ApplicationMainMenu.getInstance().getApplicationContext();
            default:
                System.out.println("The option with the entered number does not exist. Please try again.");
                readMenuOptionInput(OutputTemplates.CREW_MEMBER_MENU.getOptionNum());
        }
    }

    public void showAllCrewMembers() {
        LinkedList<CrewMember> crewMembers = new LinkedList<>(CrewServiceImpl.getInstance().findAllCrewMembers());
        if (crewMembers.size() != 0) {
            CrewWriteConsoleStream.getInstance().writeData(crewMembers);
        } else {
            System.out.println("No crew members has been created yet");
        }
    }

    public void showAvailableCrewMembers() {
        LinkedList<CrewMember> crewMembers = new LinkedList<>(CrewServiceImpl.getInstance().
                findAllCrewMembersByCriteria(CrewMemberCriteria.builder().isReadyForNextMission(true).build()));
        if (crewMembers.size() != 0) {
            CrewWriteConsoleStream.getInstance().writeData(crewMembers);
        } else {
            System.out.println("No crew members are available yet");
        }
        System.out.println();
    }

    public void createCrewMemberByUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating new crew member...");
        System.out.println("Enter unique name:");
        String name = scanner.nextLine();
        int role = CrewReadFileStream.getInstance().readRoleId(), rank = CrewReadFileStream.getInstance().readRankId();
        CrewMember crewMember;
        while (true) {
            try {
                crewMember = CrewMemberFactory.getInstance().create(name, role, rank);
                break;
            } catch (DuplicateEntityNameException e) {
                log.error("Crew member with this name already exists");
                System.out.println("Crew member with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(crewMember.toString() + " WAS CREATED SUCCESSFULLY\n");
        log.info(crewMember + " was created successfully");
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
                log.error("Generate duplicate crew member");
                System.out.println("Crew member with this name already exists. Please enter name again:");
                name = scanner.nextLine();
            }
        }
        clearConsole();
        System.out.println(crewMember.toString() + " was generated successfully\n");
        log.info(crewMember + " was generated successfully");
    }
}
