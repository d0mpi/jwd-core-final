package com.epam.jwd.core_final.util.impl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
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
public enum PlanetReadStream implements ReadStream {
    INSTANCE;

    @Override
    public void readData() {
        List<Planet> planets = new ArrayList<>();
        char fileSeparator = File.separatorChar;
        String inputDirectory = "src" + fileSeparator +
                "main" + fileSeparator +
                "resources" + fileSeparator +
                ApplicationProperties.getInstance().getInputRootDir() + fileSeparator;
        Path path = Paths.get(inputDirectory + ApplicationProperties.getInstance().getSpaceMapFileName());
        Scanner scanner;
        StringBuilder line = new StringBuilder("");
        int yCoordinate = 0, xCoordinate = 0;

        Pattern pattern = Pattern.compile("(?<planetName>[^,]+),");
        Matcher matcher;

        try {
            scanner = new Scanner(path);
            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
                if (!temp.matches("#.*")) {
                    matcher = pattern.matcher(temp);
                    while (matcher.find()) {
                        try {
                            String planetName = matcher.group("planetName");
                            if (!planetName.equals("null")) {
                                PlanetFactory.getInstance().create(planetName,
                                        new Point(xCoordinate, yCoordinate));
                            }
                            xCoordinate++;
                        } catch (DuplicateEntityNameException e) {
                            log.info("Planet with this name already exists ");
                            xCoordinate++;
                        }
                    }
                    yCoordinate++;
                    xCoordinate = 0;
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
