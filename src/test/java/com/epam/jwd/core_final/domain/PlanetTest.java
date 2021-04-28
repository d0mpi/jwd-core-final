package com.epam.jwd.core_final.domain;


import org.junit.Assert;
import org.junit.Test;

public class PlanetTest{

    @Test
    public void testGetCoordinates() {
        Planet planet = new Planet("name",new Point(1,2));
        Assert.assertEquals(planet.getCoordinates(),new Point(1,2));
    }

    @Test
    public void testSetCoordinates() {
        Planet planet = new Planet("name",new Point(1,2));
        planet.setCoordinates(new Point(3,4));
        Assert.assertEquals(planet.getCoordinates(),new Point(3,4));
    }

}