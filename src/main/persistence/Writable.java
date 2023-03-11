package persistence;

import org.json.JSONObject;

// Represents an interface for classes that can be written to file
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
