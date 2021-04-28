package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.util.ReadStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PlanetReadFileStream implements ReadStream {
    private static class SingletonHolder {
        private static final PlanetReadFileStream instance = new PlanetReadFileStream();
    }

    public static PlanetReadFileStream getInstance() {
        return PlanetReadFileStream.SingletonHolder.instance;
    }

    private PlanetReadFileStream() {
    }

    @Override
    public void readData() {
        Path path = Paths.get(buildFilePath(ApplicationProperties.getInstance().getSpaceMapFileName()));
        Scanner scanner;
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
                                log.info("Planet " + matcher.group("planetName") + " was read from file");
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
            log.info("Error during reading planet from file");
            e.printStackTrace();
        }
    }

    public int readCoordinate(char name) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter " + name + " planet coordinate:");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid value was entered. Please try again.");
            }
        } while (true);
    }

}
