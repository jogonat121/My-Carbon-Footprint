package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FootprintTest {
    private Footprint testFootprintFood;
    private Footprint testFootprintTravel;

    @BeforeEach
    void runBefore() {
        testFootprintFood = new Footprint("Food");
        testFootprintTravel = new Footprint("Travel");
    }

    @Test
    void testFootprint() {
        assertEquals(0, testFootprintFood.getValue());
        assertEquals("Food", testFootprintFood.getCategory());

        assertEquals(0, testFootprintTravel.getValue());
        assertEquals("Travel", testFootprintTravel.getCategory());
    }

    @Test
    void addValue() {
        testFootprintFood.addValue(1);
        assertEquals(1.0, testFootprintFood.getValue());

        testFootprintTravel.addValue(10.5);
        testFootprintTravel.addValue(15.1);
        assertEquals(25.6, testFootprintTravel.getValue());
    }
}