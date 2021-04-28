package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.OutputTemplates;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.ReadStream;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CrewReadFileStream implements ReadStream {
    private static class SingletonHolder {
        private static final CrewReadFileStream instance = new CrewReadFileStream();
    }

    public static CrewReadFileStream getInstance() {
        return CrewReadFileStream.SingletonHolder.instance;
    }

    private CrewReadFileStream() {
    }

    @Override
    public void readData() {
        Matcher matcher = createMatcher(readFileToString(ApplicationProperties.getInstance().getCrewFileName()));
        while (matcher.find()) {
            try {
                CrewMemberFactory.getInstance().create(matcher.group("name"),
                        matcher.group("role"),
                        matcher.group("rank"));
                log.info("Crew " + matcher.group("name") + " was read from file");
            } catch (DuplicateEntityNameException e) {
                log.error("Crew member name " + matcher.group("name")  + " already exists");
            }
        }
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


    public Matcher createMatcher(String text) {
        Pattern pattern = Pattern.compile("(?<role>[0-9]+),(?<name>[^,]+),(?<rank>[0-9]+);");
        return pattern.matcher(text);
    }
}
