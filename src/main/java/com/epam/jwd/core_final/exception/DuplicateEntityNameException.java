package com.epam.jwd.core_final.exception;

public class DuplicateEntityNameException extends Exception {
    private final String entityName;

    public DuplicateEntityNameException(String entityName) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity " + entityName + " already exists";
    }
}
