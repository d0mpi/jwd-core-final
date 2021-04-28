package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.impl.ApplicationMainMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            log.info("Start Nassa Program...");
            Application.start();
            ApplicationMainMenu.getInstance().getApplicationContext();

        } catch (InvalidStateException e) {
            log.error("Failed to start program!");
        }
    }
}

