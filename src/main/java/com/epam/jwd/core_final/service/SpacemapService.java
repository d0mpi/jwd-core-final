package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;

public interface SpacemapService {

    Planet getRandomPlanet();

    // Dijkstra ?
    Long getDistanceBetweenPlanets(Planet first, Planet second);

    Planet createPlanet(Planet planet) throws DuplicateEntityNameException;
}
