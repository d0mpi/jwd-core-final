package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.ReadStream;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public enum CrewReadStream implements ReadStream {
    INSTANCE;

    @Override
    public void readData() {
        List<CrewMember> crewMembers = new ArrayList<>();
        char fileSeparator = File.separatorChar;
        String inputDirectory = "src" + fileSeparator +
                "main" + fileSeparator +
                "resources" + fileSeparator +
                ApplicationProperties.getInstance().getInputRootDir() + fileSeparator;
        Path path = Paths.get(inputDirectory + ApplicationProperties.getInstance().getCrewFileName());
        Scanner scanner;
        StringBuilder line = new StringBuilder("");

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

        Pattern pattern = Pattern.compile("(?<role>[0-9]+),(?<name>[^,]+),(?<rank>[0-9]+);");
        Matcher matcher = pattern.matcher(line);
        CrewMemberFactory crewMemberFactory = CrewMemberFactory.getInstance();
        while(matcher.find()){
            try {
                crewMemberFactory.create(matcher.group("name"),
                        matcher.group("role"),
                        matcher.group("rank"));
            } catch (DuplicateEntityNameException e) {
                log.info("Crew member with this name already exists ");
            }
        }
    }
}
