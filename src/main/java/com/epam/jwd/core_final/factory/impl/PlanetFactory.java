package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.PlanetServiceImpl;

public class PlanetFactory implements EntityFactory<Planet> {

    private static class SingletonHolder {
        private static final PlanetFactory instance = new PlanetFactory();
    }

    public static PlanetFactory getInstance() {
        return SingletonHolder.instance;
    }

    private PlanetFactory() {
    }

    @Override
    public Planet create(Object... args) throws DuplicateEntityNameException {
        Planet planet;
        try {
            planet = new Planet(String.valueOf(args[0]), (Point) args[1]);
            PlanetServiceImpl.getInstance().createPlanet(planet);
            return planet;
        } catch (NumberFormatException | UnknownEntityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
