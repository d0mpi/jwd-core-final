package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    private static class SingletonHolder {
        private static final SpaceshipFactory instance = new SpaceshipFactory();
    }

    public static SpaceshipFactory getInstance() {
        return SpaceshipFactory.SingletonHolder.instance;
    }

    private SpaceshipFactory() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public Spaceship create(Object ... args) {
        Spaceship spaceship = null;
        try {
            spaceship = new Spaceship(String.valueOf(args[0]),
                    (Map<Role, Short>) args[1],
                    Long.parseLong(String.valueOf(args[2])));
            SpaceshipServiceImpl.getInstance().createSpaceship(spaceship);
            return spaceship;
        } catch (NumberFormatException | DuplicateEntityNameException e) {
            e.printStackTrace();
        }
        return null;
    }
}
