package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;

public class PlanetFactory implements EntityFactory<Planet> {

    private static class SingletonHolder{
        private static final PlanetFactory instance = new PlanetFactory();
    }
    private static PlanetFactory getInstance(){
        return SingletonHolder.instance;
    }
    private PlanetFactory(){}

    @Override
    public Planet create(Object... args) {
        return null;
    }
}
