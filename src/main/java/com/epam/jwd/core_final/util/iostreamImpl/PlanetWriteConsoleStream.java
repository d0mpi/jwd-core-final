package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.util.WriteStream;

import java.util.List;

public class PlanetWriteConsoleStream implements WriteStream {
    private static class SingletonHolder {
        private static final PlanetWriteConsoleStream instance = new PlanetWriteConsoleStream();
    }

    public static PlanetWriteConsoleStream getInstance() {
        return PlanetWriteConsoleStream.SingletonHolder.instance;
    }

    private PlanetWriteConsoleStream() {
    }

    @Override
    public <T extends AbstractBaseEntity> void writeData(List<T> planets) {
        for (T planet : planets) {
            System.out.println(planet.toString());
        }
        System.out.println();
    }
}
