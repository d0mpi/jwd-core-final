package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.ApplicationMainMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.runnableImpl.DataChangeRunnable;
import com.epam.jwd.core_final.util.runnableImpl.FileTimetableRunnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface Application {
    static void start() throws InvalidStateException {
        final ApplicationMainMenu applicationMenu = ApplicationMainMenu.getInstance();
        final NassaContext nassaContext = NassaContext.getInstance();

        nassaContext.init();

        ScheduledExecutorService dataChange = Executors.newSingleThreadScheduledExecutor();
        dataChange.scheduleAtFixedRate(DataChangeRunnable.getInstance(), 0,
                1, TimeUnit.SECONDS);

        ScheduledExecutorService realtimeTimetableToFile = Executors.newSingleThreadScheduledExecutor();
        realtimeTimetableToFile.scheduleAtFixedRate(FileTimetableRunnable.getInstance(), 0,
                ApplicationProperties.getInstance().getFileRefreshRate(), TimeUnit.SECONDS);

        applicationMenu.getApplicationContext();
    }

}
