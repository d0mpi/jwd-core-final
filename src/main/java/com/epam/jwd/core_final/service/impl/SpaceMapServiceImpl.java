package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

public class SpaceMapServiceImpl implements SpacemapService {

    private static class SingletonHolder {
        private static final SpaceMapServiceImpl instance = new SpaceMapServiceImpl();
    }

    public static SpaceMapServiceImpl getInstance() {
        return SpaceMapServiceImpl.SingletonHolder.instance;
    }

    private SpaceMapServiceImpl() {
    }

    @Override
    public Planet getRandomPlanet() {
        return null;
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        return 0;
    }
}
