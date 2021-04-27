package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.util.ReadStream;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public enum SpaceshipReadStream implements ReadStream {
    INSTANCE;

    @Override
    public void readData() {
        List<Spaceship> spaceships = new ArrayList<>();
        char fileSeparator = File.separatorChar;
        String inputDirectory = "src" + fileSeparator +
                "main" + fileSeparator +
                "resources" + fileSeparator +
                ApplicationProperties.getInstance().getInputRootDir() + fileSeparator;
        Path path = Paths.get(inputDirectory + ApplicationProperties.getInstance().getSpaceshipsFileName());
        Scanner scanner = null;
        StringBuilder line = new StringBuilder();

        try {
            scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
                if (!temp.matches("#.*")) {
                    line.append(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("(?<name>[a-zA-Z\\s]+);(?<distance>[0-9]+);" +
                "\\{(?<roleID1>[0-9]+):(?<count1>[0-9]+)," +
                "(?<roleID2>[0-9]+):(?<count2>[0-9]+)," +
                "(?<roleID3>[0-9]+):(?<count3>[0-9]+)," +
                "(?<roleID4>[0-9]+):(?<count4>[0-9]+)}");
        Matcher matcher = pattern.matcher(line);

        SpaceshipFactory spaceshipFactory = SpaceshipFactory.getInstance();
        while (matcher.find()) {
            Map<Role, Short> crewRoleMap = new HashMap<>();
            crewRoleMap.put(Role.resolveRoleById(Integer.parseInt(matcher.group("roleID1"))),
                    Short.parseShort(matcher.group("count1")));
            crewRoleMap.put(Role.resolveRoleById(Integer.parseInt(matcher.group("roleID2"))),
                    Short.parseShort(matcher.group("count2")));
            crewRoleMap.put(Role.resolveRoleById(Integer.parseInt(matcher.group("roleID3"))),
                    Short.parseShort(matcher.group("count3")));
            crewRoleMap.put(Role.resolveRoleById(Integer.parseInt(matcher.group("roleID4"))),
                    Short.parseShort(matcher.group("count4")));
            try {
                spaceshipFactory.create(matcher.group("name"),crewRoleMap,matcher.group("distance"));
            } catch (DuplicateEntityNameException e) {
                log.info("Spaceship with this name already exists");
            }
        }
    }
}
