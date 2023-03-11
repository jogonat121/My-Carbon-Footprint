package persistence;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Represents a reader that reads footprint record from JSON data stored in file at path
public class JsonReader {
    private final String path;

    // EFFECTS: constructs a reader with path to JSON file
    public JsonReader(String fileName) {
        this.path = "./data/" + fileName + ".json";
    }

    // EFFECTS: returns the path
    public String getPath() {
        return path;
    }

    // EFFECTS: reads and parses the JSON data; returns footprint record;
    // throws CannotAccessDataException if error occurs while reading data
    public FootprintRecord read() throws CannotAccessDataException {
        try {
            String jsonData = Files.readString(Paths.get(path));
            JSONObject json = new JSONObject(jsonData);
            return parseFootprintRecord(json);
        } catch (Exception e) {
            throw new CannotAccessDataException("Cannot read data from file");
        }
    }

    // EFFECTS: parses the JSON data and returns footprint record
    private FootprintRecord parseFootprintRecord(JSONObject json) {
        String id = json.getString("id");
        JSONArray jsonFootprints = json.getJSONArray("footprints");
        List<Footprint> footprints = parseFootprints(jsonFootprints);

        return new FootprintRecord(id, footprints.get(0), footprints.get(1), footprints.get(2));
    }

    // EFFECTS: returns a list of footprints stored as JSON objects in the given JSON array
    private List<Footprint> parseFootprints(JSONArray jsonArray) {
        List<Footprint> footprints = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject json = new JSONObject(obj.toString());
            String category = json.getString("category");
            double value = json.getDouble("value");

            Footprint footprint = new Footprint(category);
            footprint.setValue(value);
            footprints.add(footprint);
        }

        return footprints;
    }
}
