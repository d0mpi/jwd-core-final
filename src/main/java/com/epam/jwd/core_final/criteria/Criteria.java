package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;
import lombok.*;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
@ToString
public abstract class Criteria<T extends BaseEntity> {
    @Getter
    private final Long id;
    @Getter
    private final String name;

    public Criteria(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

