package data.exceptions;

public class RecordNotFoundException extends Exception {
    private final String id;

    public RecordNotFoundException(String id) {
        super("Record with " + id + " not found");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
