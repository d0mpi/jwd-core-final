package com.epam.jwd.core_final.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity {
    @Getter
    @Setter
    private Point coordinates;

    public Planet(String name, Point coordinates) {
        super(name);
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Planet " + getName() + " (" + coordinates.getX() + ";" + coordinates.getY() + ")";
    }

}
