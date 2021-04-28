package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.util.ReadStream;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SpaceshipReadFileStream implements ReadStream {

    private static class SingletonHolder {
        private static final SpaceshipReadFileStream instance = new SpaceshipReadFileStream();
    }

    public static SpaceshipReadFileStream getInstance() {
        return SpaceshipReadFileStream.SingletonHolder.instance;
    }

    private SpaceshipReadFileStream() {
    }

    @Override
    public void readData() {
        Matcher matcher = createMatcher(readFileToString(ApplicationProperties.getInstance().getSpaceshipsFileName()));
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
                SpaceshipFactory.getInstance().create(matcher.group("name"), crewRoleMap, matcher.group("distance"));
                log.info("Spaceship " + matcher.group("name") + " was read from file");
            } catch (DuplicateEntityNameException e) {
                log.info("Spaceship with name " + matcher.group("name") + " already exists");
            }
        }
    }

    public Matcher createMatcher(String text) {
        Pattern pattern = Pattern.compile("(?<name>[a-zA-Z\\s]+);(?<distance>[0-9]+);" +
                "\\{(?<roleID1>[0-9]+):(?<count1>[0-9]+)," +
                "(?<roleID2>[0-9]+):(?<count2>[0-9]+)," +
                "(?<roleID3>[0-9]+):(?<count3>[0-9]+)," +
                "(?<roleID4>[0-9]+):(?<count4>[0-9]+)}");
        return pattern.matcher(text);
    }

}
