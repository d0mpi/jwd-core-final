package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.ApplicationMainMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.util.TimerMissionWriter;

import javax.management.StandardEmitterMBean;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public interface Application {
    static void start() throws InvalidStateException {
        final ApplicationMainMenu applicationMenu = ApplicationMainMenu.getInstance();
        final NassaContext nassaContext = NassaContext.getInstance();

        nassaContext.init();

//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(TimerMissionWriter.getInstance(), 0,
//                1, TimeUnit.SECONDS);
//        Timer timer = new Timer();
//        timer.schedule(TimerMissionWriter.getInstance(),0,1000);


        applicationMenu.getApplicationContext();
    }
}
