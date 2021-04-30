package com.epam.jwd.core_final.util.runnableImpl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataChangeRunnable implements Runnable {
    private static class SingletonHolder {
        private static final DataChangeRunnable instance = new DataChangeRunnable();
    }

    public static DataChangeRunnable getInstance() {
        return DataChangeRunnable.SingletonHolder.instance;
    }

    private DataChangeRunnable() {
    }

    @Override
    public void run() {
        NassaContext.getInstance().setCurrentDate(
                NassaContext.getInstance().getCurrentDate().plusDays(1));
    }
}
