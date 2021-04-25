package com.epam.jwd.core_final.iostream.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.iostream.ReadStream;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CrewReadStream implements ReadStream {
    INSTANCE;

    @Override
    public List<CrewMember> readData() {
        List<CrewMember> crewMembers = new ArrayList<>();
        char fileSeparator = File.separatorChar;
        String inputDirectory = "src" + fileSeparator +
                "main" + fileSeparator +
                "resources" + fileSeparator +
                "input" + fileSeparator;
        Path path = Paths.get(inputDirectory + ApplicationProperties.getInstance().getCrewFileName());
        Scanner scanner = null;
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
            crewMemberFactory.create(matcher.group("name"),
                    matcher.group("role"),
                    matcher.group("rank"));
        }
        return crewMembers;
    }
}
