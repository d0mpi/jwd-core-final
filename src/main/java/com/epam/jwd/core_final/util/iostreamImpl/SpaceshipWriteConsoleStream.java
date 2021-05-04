package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.util.WriteStream;

import java.util.List;

public class SpaceshipWriteConsoleStream implements WriteStream<Spaceship> {
    private static class SingletonHolder {
        private static final SpaceshipWriteConsoleStream instance = new SpaceshipWriteConsoleStream();
    }

    public static SpaceshipWriteConsoleStream getInstance() {
        return SpaceshipWriteConsoleStream.SingletonHolder.instance;
    }

    private SpaceshipWriteConsoleStream() {
    }

    @Override
    public void writeData(List<Spaceship> spaceships) {
        for (Spaceship spaceship : spaceships) {
            System.out.println(spaceship.toString());
        }
        System.out.println();
    }
}
