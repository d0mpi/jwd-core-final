package com.epam.jwd.core_final.exception;

public class NotReadyEntityException extends Exception {
    private final String entityName;

    public NotReadyEntityException(String entityName) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity " + entityName + " does not ready for the next mission";
    }
}
