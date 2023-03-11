package persistence;

import data.FootprintRecord;
import model.Footprint;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFootprintRecord(FootprintRecord expected, FootprintRecord actual) {
        Footprint expectedFoodFootprint = expected.getFoodFootprint();
        Footprint expectedTravelFootprint = expected.getTravelFootprint();
        Footprint expectedMiscFootprint = expected.getMiscFootprint();
        Footprint actualFoodFootprint = actual.getFoodFootprint();
        Footprint actualTravelFootprint = actual.getTravelFootprint();
        Footprint actualMiscFootprint = actual.getMiscFootprint();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expectedFoodFootprint.getCategory(),actualFoodFootprint.getCategory());
        assertEquals(expectedTravelFootprint.getCategory(), actualTravelFootprint.getCategory());
        assertEquals(expectedMiscFootprint.getCategory(), actualMiscFootprint.getCategory());
        assertEquals(expectedFoodFootprint.getValue(),actualFoodFootprint.getValue());
        assertEquals(expectedTravelFootprint.getValue(), actualTravelFootprint.getValue());
        assertEquals(expectedMiscFootprint.getValue(), actualMiscFootprint.getValue());
    }
}
