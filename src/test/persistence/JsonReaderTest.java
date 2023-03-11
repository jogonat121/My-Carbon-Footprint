package persistence;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    private FootprintRecord footprintRecord;
    private JsonReader jsonReader;

    @BeforeEach
    void runBefore() {
        Footprint foodFootprint = new Footprint("Food");
        Footprint travelFootprint = new Footprint("Travel");
        Footprint miscFootprint = new Footprint("Misc.");
        foodFootprint.setValue(11.5);
        travelFootprint.setValue(15);
        miscFootprint.setValue(0.25);
        footprintRecord = new FootprintRecord("test",
                foodFootprint, travelFootprint, miscFootprint);
        try {
            footprintRecord.exportFile("test");
        } catch (CannotAccessDataException e) {
            fail();
        }

        jsonReader = new JsonReader("test");
    }

    @Test
    void testJsonReader() {
        assertEquals("./data/test.json", jsonReader.getPath());
    }

    @Test
    void testReadInvalidFile() {
        try {
            JsonReader invalidFileReader = new JsonReader("someIllegal||??///fileName%(@^(");
            invalidFileReader.read();
            fail("CannotAccessDataException expected");
        } catch (CannotAccessDataException e) {
            // expected
        }
    }

    @Test
    void testReadTypicalCase() {
        try {
            FootprintRecord resFootprintRecord = jsonReader.read();
            checkFootprintRecord(footprintRecord, resFootprintRecord);
        } catch (CannotAccessDataException e) {
            fail();
        }
    }
}
