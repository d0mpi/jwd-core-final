package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.ApplicationMainMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

public interface Application {
    static void start() throws InvalidStateException {
        final ApplicationMainMenu applicationMenu = ApplicationMainMenu.getInstance();
        final NassaContext nassaContext = NassaContext.getInstance();

        nassaContext.init();
        applicationMenu.getApplicationContext();
    }
}
