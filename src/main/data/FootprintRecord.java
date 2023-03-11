package data;

import com.opencsv.CSVWriter;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileWriter;
import java.io.IOException;

// Represents footprint record with a unique id, and food, travel and misc. footprints
public class FootprintRecord implements Writable {
    private final String id;
    private static final String PATH = "./data/userRecords.csv";
    private final Footprint foodFootprint;
    private final Footprint travelFootprint;
    private final Footprint miscFootprint;

    // REQUIRES: id should be unique (no other FootprintRecord should have the same id)
    // EFFECTS: constructs a footprint record with the given id and footprints
    public FootprintRecord(String id, Footprint food, Footprint travel, Footprint misc) {
        this.id = id;
        foodFootprint = food;
        travelFootprint = travel;
        miscFootprint = misc;
    }

    // EFFECTS: returns the ID
    public String getId() {
        return id;
    }

    // EFFECTS: returns the food footprint
    public Footprint getFoodFootprint() {
        return foodFootprint;
    }

    // EFFECTS: returns the travel footprint
    public Footprint getTravelFootprint() {
        return travelFootprint;
    }

    // EFFECTS: returns the misc. footprint
    public Footprint getMiscFootprint() {
        return miscFootprint;
    }

    // MODIFIES: data
    // EFFECTS: writes the footprint record to the data at the path;
    // throws CannotAccessDataException if error occurs while writing data
    public boolean saveData(String pathName, boolean append, boolean testException) throws CannotAccessDataException {
        try {
            if (testException) {
                throw new IOException();
            }
            CSVWriter writer = new CSVWriter(new FileWriter(pathName, append), ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            if (!append) {
                String[] header =  new String[] {"ID", "Food Footprint", "Travel Footprint", "Misc. Footprint",
                        "Total Footprint"};
                writer.writeNext(header);
            }

            String[] record =  new String[] {String.valueOf(id),
                    String.valueOf(foodFootprint.getValue()),
                    String.valueOf(travelFootprint.getValue()),
                    String.valueOf(miscFootprint.getValue()), String.valueOf(getTotalValue())};
            writer.writeNext(record);

            writer.close();
        } catch (Exception e) {
            throw new CannotAccessDataException("Cannot write data to file");
        }

        return true;
    }

    // MODIFIES: data
    // EFFECTS: writes the footprint record to the data;
    // throws CannotAccessDataException if error occurs while writing data
    public boolean saveData() throws CannotAccessDataException {
        return saveData(PATH, true, false);
    }

    // EFFECTS: returns the total footprint value
    public double getTotalValue() {
        double totalValue = foodFootprint.getValue() + travelFootprint.getValue() + miscFootprint.getValue();
        return Math.round(totalValue * 1000) / 1000.0;
    }

    // EFFECTS: saves this as JSON file at path with given filename;
    // throws CannotAccessDataException if error occurs while writing file
    public void exportFile(String fileName) throws CannotAccessDataException {
        JsonWriter writer = new JsonWriter(fileName);
        writer.write(this);
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("footprints", footprintsToJson());
        return json;
    }

    // EFFECTS: returns a JSON array containing JSON object each of food, travel and misc. footprints
    private JSONArray footprintsToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(foodFootprint.toJson());
        jsonArray.put(travelFootprint.toJson());
        jsonArray.put(miscFootprint.toJson());
        return jsonArray;
    }
}
