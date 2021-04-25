package com.epam.jwd.core_final.exception;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final int id;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.id = 0;
    }

    public UnknownEntityException(String entityName, int id) {
        super();
        this.entityName = entityName;
        this.id = id;
    }

    @Override
    public String getMessage() {
        if (id != 0) {
            return "Entity " + entityName + " with id " + id + " does not exist";
        } else {
            return "Entity " + entityName + " does not exist";
        }
    }
}
