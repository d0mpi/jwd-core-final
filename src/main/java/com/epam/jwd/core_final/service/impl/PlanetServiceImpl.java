package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.service.PlanetService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Slf4j
public class PlanetServiceImpl implements PlanetService {
    private final LinkedList<Planet> planets = new LinkedList<>(NassaContext.getInstance().retrieveBaseEntityList(Planet.class));


    private static class SingletonHolder {
        private static final PlanetServiceImpl instance = new PlanetServiceImpl();
    }

    public static PlanetServiceImpl getInstance() {
        return PlanetServiceImpl.SingletonHolder.instance;
    }

    private PlanetServiceImpl() {
    }

    public List<Planet> findAllPlanets() {
        return planets;
    }

    @Override
    public Planet getRandomPlanet() {
        Random random = new Random();
        return planets.get(random.nextInt(planets.size() - 1));
    }

    @Override
    public int generateRandomCoordinate() {
        Random random = new Random();
        return random.nextInt(300);
    }

    @Override
    public Long getDistanceBetweenPlanets(Planet first, Planet second) {
        return Math.round(Math.sqrt(
                Math.pow(first.getCoordinates().getX() - second.getCoordinates().getX(), 2) +
                        Math.pow(first.getCoordinates().getY() - second.getCoordinates().getY(), 2)));
    }

    @Override
    public void createPlanet(Planet planet) throws DuplicateEntityNameException {
        Planet duplicatePlanet = planets.stream().
                filter(member -> member.getName().equalsIgnoreCase(planet.getName())).findFirst().orElse(null);
        if (duplicatePlanet != null) {
            throw new DuplicateEntityNameException(planet.getName());
        } else {
            planets.add(planet);
        }
        log.info("New planet was added to collection");
    }
}
