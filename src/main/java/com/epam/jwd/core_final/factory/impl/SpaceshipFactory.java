package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityNameException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
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
    public Spaceship create(Object... args) throws DuplicateEntityNameException {
        Spaceship spaceship;
        try {
            spaceship = new Spaceship(String.valueOf(args[0]),
                    (Map<Role, Short>) args[1],
                    Long.parseLong(String.valueOf(args[2])));
            SpaceshipServiceImpl.getInstance().createSpaceship(spaceship);
            log.info("New spaceship " + spaceship + " was created successfully");
            return spaceship;
        } catch (NumberFormatException e) {
            log.error("Error during creating new spaceship. Check args");
            e.printStackTrace();
        }
        return null;
    }
}
