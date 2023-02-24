package data;

import data.exceptions.CannotAccessDataException;
import data.exceptions.RecordNotFoundException;
import model.Footprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRecordsTest {
    private FootprintRecord testFootprintRecord;
    private Footprint testFootprintFood;
    private Footprint testFootprintTravel;
    private Footprint testFootprintMisc;
    private UserRecords testUserRecords;
    private static final String PATH_TO_SAMPLE_RECORDS = "./data/testUserRecords.csv";

    @BeforeEach
    void runBefore() {
        try {
            testFootprintFood = new Footprint("Food");
            testFootprintTravel = new Footprint("Travel");
            testFootprintMisc = new Footprint("Misc.");
            testFootprintRecord = new FootprintRecord("testID",
                    testFootprintFood, testFootprintTravel, testFootprintMisc);
            testFootprintFood.setValue(1);
            testFootprintTravel.setValue(2);
            testFootprintMisc.setValue(3);

            testFootprintRecord.saveData(PATH_TO_SAMPLE_RECORDS, false);

            testUserRecords = new UserRecords();
            testUserRecords.init(PATH_TO_SAMPLE_RECORDS);
        } catch (CannotAccessDataException e) {
            fail();
        }
    }

    @Test
    void testUserRecords() {
        assertEquals(UserRecords.PATH, testUserRecords.getPath());

        assertEquals(testFootprintRecord.getId(), testUserRecords.getRecords().get(1)[0]);
        assertEquals(testFootprintFood.getValue(), Double.parseDouble(testUserRecords.getRecords().get(1)[1]));
        assertEquals(testFootprintTravel.getValue(), Double.parseDouble(testUserRecords.getRecords().get(1)[2]));
        assertEquals(testFootprintMisc.getValue(), Double.parseDouble(testUserRecords.getRecords().get(1)[3]));
        assertEquals(testFootprintRecord.getTotalValue(), Double.parseDouble(testUserRecords.getRecords().get(1)[4]));
    }

    @Test
    void testRemoveRecordNotExist() {
        try {
            assertFalse(testUserRecords.removeRecord("unknown"));
            assertEquals(2, testUserRecords.getRecords().size());
            fail("RecordNotFoundException expected");
        } catch (CannotAccessDataException e) {
            fail();
        } catch (RecordNotFoundException e) {
            assertEquals(e.getId(), "unknown");
        }
    }

    @Test
    void testRemoveRecord() {
        try {
            assertTrue(testUserRecords.removeRecord(testFootprintRecord.getId()));
            assertEquals(1, testUserRecords.getRecords().size());
        } catch (CannotAccessDataException | RecordNotFoundException e) {
            fail();
        }
    }

    @Test
    void testGetRecordIndex() {
        assertEquals(0, testUserRecords.getRecordIndex("unknown"));
        assertEquals(1, testUserRecords.getRecordIndex(testFootprintRecord.getId()));
    }

    @Test
    void testGetAverages() {
        List<Double> averages = testUserRecords.getAverages();
        assertEquals(testFootprintFood.getValue(), averages.get(0));
        assertEquals(testFootprintTravel.getValue(), averages.get(1));
        assertEquals(testFootprintMisc.getValue(), averages.get(2));
        assertEquals(testFootprintRecord.getTotalValue(), averages.get(3));
    }
}
