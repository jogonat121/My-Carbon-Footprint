package persistence;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private FootprintRecord footprintRecord;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

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
        jsonReader = new JsonReader("test");

        jsonWriter = new JsonWriter("test");
    }

    @Test
    void testJsonWriter() {
        assertEquals("./data/test.json", jsonWriter.getPath());
    }

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWriter invalidFileWriter = new JsonWriter("someIllegal||??///fileName%(@^(");
            invalidFileWriter.write(footprintRecord);
            fail("CannotAccessDataException expected");
        } catch (CannotAccessDataException e) {
            // expected
        }
    }

    @Test
    void testWriteTypicalCase() {
        try {
            jsonWriter.write(footprintRecord);
            FootprintRecord resFootprintRecord = jsonReader.read();

            checkFootprintRecord(footprintRecord, resFootprintRecord);
        } catch (CannotAccessDataException e) {
            fail();
        }
    }
}
