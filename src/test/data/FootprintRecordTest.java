package data;

import data.exceptions.CannotAccessDataException;
import data.exceptions.RecordNotFoundException;
import model.Footprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FootprintRecordTest {
    private FootprintRecord testFootprintRecord;
    private Footprint testFootprintFood;
    private Footprint testFootprintTravel;
    private Footprint testFootprintMisc;
    private static final String PATH_TO_SAMPLE_RECORDS = "./data/testUserRecords.csv";

    @BeforeEach
    void runBefore() {
        testFootprintFood = new Footprint("Food");
        testFootprintTravel = new Footprint("Travel");
        testFootprintMisc = new Footprint("Misc.");
        testFootprintRecord = new FootprintRecord("testID",
                testFootprintFood, testFootprintTravel, testFootprintMisc);

        testFootprintFood.setValue(1);
        testFootprintTravel.setValue(2);
        testFootprintMisc.setValue(3);
    }

    @Test
    void testFootprintRecord() {
        assertEquals("testID", testFootprintRecord.getId());
        assertEquals(testFootprintFood, testFootprintRecord.getFoodFootprint());
        assertEquals(testFootprintTravel, testFootprintRecord.getTravelFootprint());
        assertEquals(testFootprintMisc, testFootprintRecord.getMiscFootprint());
    }

    @Test
    void testSaveDataException() {
        try {
            testFootprintRecord.saveData(PATH_TO_SAMPLE_RECORDS, false, true);
            fail("CannotAccessDataException expected");
        } catch (CannotAccessDataException e) {
            // expected
        }
    }

    @Test
    void testSaveDataDefaultParams() {
        try {
            testFootprintRecord.saveData();
            UserRecords userRecords = new UserRecords();
            userRecords.init();
            String[] record = userRecords.getRecords().get(1);

            assertEquals(testFootprintRecord.getId(), record[0]);
            assertEquals(testFootprintFood.getValue(), Double.parseDouble(record[1]));
            assertEquals(testFootprintTravel.getValue(), Double.parseDouble(record[2]));
            assertEquals(testFootprintMisc.getValue(), Double.parseDouble(record[3]));
            assertEquals(testFootprintRecord.getTotalValue(), Double.parseDouble(record[4]));
            assertTrue(testFootprintRecord.saveData());

            userRecords.removeRecord(testFootprintRecord.getId(), false);
        } catch (CannotAccessDataException | RecordNotFoundException e) {
            fail();
        }
    }

    @Test
    void testSaveData() {
        try {
            testFootprintRecord.saveData(PATH_TO_SAMPLE_RECORDS, false, false);
            UserRecords userRecords = new UserRecords();
            userRecords.init(PATH_TO_SAMPLE_RECORDS, false);
            String[] record = userRecords.getRecords().get(1);

            assertEquals(testFootprintRecord.getId(), record[0]);
            assertEquals(testFootprintFood.getValue(), Double.parseDouble(record[1]));
            assertEquals(testFootprintTravel.getValue(), Double.parseDouble(record[2]));
            assertEquals(testFootprintMisc.getValue(), Double.parseDouble(record[3]));
            assertEquals(testFootprintRecord.getTotalValue(), Double.parseDouble(record[4]));
        } catch (CannotAccessDataException e) {
            fail();
        }
    }

    @Test
    void testGetTotalValue() {
        assertEquals(testFootprintFood.getValue()
                + testFootprintTravel.getValue()
                + testFootprintMisc.getValue(),
                testFootprintRecord.getTotalValue());

        double travelFootprintValue = testFootprintTravel.getValue();
        testFootprintTravel.setValue(travelFootprintValue + 3.51);
        assertEquals(testFootprintFood.getValue()
                + travelFootprintValue
                + testFootprintMisc.getValue()
                + 3.51,
                testFootprintRecord.getTotalValue());
    }
}
