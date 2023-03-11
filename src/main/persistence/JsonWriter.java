package persistence;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import org.json.JSONObject;

import java.io.FileWriter;

// Represents a writer that writes footprint record as JSON file at path
public class JsonWriter {
    private static final int TAB = 4;
    private final String path;

    // EFFECTS: constructs a writer with path to JSON file
    public JsonWriter(String fileName) {
        this.path = "./data/" + fileName + ".json";
    }

    // EFFECTS: return the path
    public String getPath() {
        return path;
    }

    // MODIFIES: this
    // EFFECTS: parses the footprint record as JSON data and writes to file;
    // throws CannotAccessDataException if error occurs while writing data
    public void write(FootprintRecord fr) throws CannotAccessDataException {
        JSONObject json = fr.toJson();
        writeToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON string to file;
    // throws CannotAccessDataException if error occurs while writing to file
    private void writeToFile(String data) throws CannotAccessDataException {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            throw new CannotAccessDataException("Cannot write data to file");
        }
    }
}
