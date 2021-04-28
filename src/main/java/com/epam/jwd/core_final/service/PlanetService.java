package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;

public interface PlanetService {

    Planet getRandomPlanet();

    int generateRandomCoordinate();

    // Dijkstra ?
    Long getDistanceBetweenPlanets(Planet first, Planet second);

    void createPlanet(Planet planet) throws DuplicateEntityNameException;
}
