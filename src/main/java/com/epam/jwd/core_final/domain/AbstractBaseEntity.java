package com.epam.jwd.core_final.domain;

import lombok.ToString;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */

@ToString
public abstract class AbstractBaseEntity implements BaseEntity {

    private static Long idCounter = 1L;
    private final Long id;
    private final String name;

    AbstractBaseEntity(String name) {
        this.id = idCounter++;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
