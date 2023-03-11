package data;

import com.opencsv.*;
import data.exceptions.CannotAccessDataException;
import data.exceptions.RecordNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents records of user footprints with path to CSV file
public class UserRecords {
    private final String path;
    public static final String PATH = "./data/userRecords.csv";
    private List<String[]> records;
    private static final char DELIMITER = ';';

    // EFFECTS: constructs and initializes a user records with path to CSV data file and empty records;
    // throws CannotAccessDataException if error occurs while reading data
    public UserRecords() throws CannotAccessDataException {
        path = PATH;
        records = new ArrayList<>();

        init();
    }

    // EFFECTS: returns path
    public String getPath() {
        return path;
    }

    // EFFECTS: returns records
    public List<String[]> getRecords() {
        return records;
    }

    // MODIFIES: this
    // EFFECTS: loads the records from the data file at the path;
    // throws CannotAccessDataException if error occurs while reading data
    public void init(String pathName, boolean testException) throws CannotAccessDataException {
        try {
            if (testException) {
                throw new IOException();
            }
            CSVParser parser = new CSVParserBuilder().withSeparator(DELIMITER).build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(pathName)).withCSVParser(parser).build();
            records = reader.readAll();
            reader.close();
        } catch (Exception e) {
            throw new CannotAccessDataException("Cannot read the CSV file specified");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the records from the data file;
    // throws CannotAccessDataException if error occurs while reading data
    void init() throws CannotAccessDataException {
        init(path, false);
    }

    // MODIFIES: this
    // EFFECTS: removes the record of the given ID and returns true, false if record not found;
    // throws CannotAccessDataException if error occurs while reading data;
    // throws RecordNotFoundException if cannot find record with given id;
    public boolean removeRecord(String id, boolean testException)
            throws CannotAccessDataException, RecordNotFoundException {
        try {
            if (testException) {
                throw new IOException();
            }
            int recordIndex = getRecordIndex(id);
            if (recordIndex == 0) {
                throw new RecordNotFoundException(id);
            }
            records.remove(recordIndex);

            CSVWriter writer = new CSVWriter(new FileWriter(path), ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            writer.writeAll(records);
            writer.close();
        } catch (IOException e) {
            throw new CannotAccessDataException("Cannot write data to file");
        }

        return true;
    }

    // EFFECTS: returns the index of the record with the given ID, 0 if record not found
    public int getRecordIndex(String id) {
        int recordIndex = 0;
        int index = 0;
        for (String[] record : records) {
            if (record[0].equals(id)) {
                recordIndex = index;
                break;
            }
            index++;
        }

        return recordIndex;
    }

    // EFFECTS: returns the average values of the food, travel, misc. and total footprints
    public List<Double> getAverages() {
        double foodFootprintValue = 0;
        double travelFootprintValue = 0;
        double miscFootprintValue = 0;
        double totalFootprintValue = 0;
        List<Double> averages = new ArrayList<>();

        for (String[] record : records.subList(1, records.size())) {
            foodFootprintValue += Double.parseDouble(record[1]);
            travelFootprintValue += Double.parseDouble(record[2]);
            miscFootprintValue += Double.parseDouble(record[3]);
            totalFootprintValue += Double.parseDouble(record[4]);
        }

        int recordsNum = records.size() - 1;
        averages.add(Math.round((foodFootprintValue / recordsNum) * 1000) / 1000.0);
        averages.add(Math.round((travelFootprintValue / recordsNum) * 1000) / 1000.0);
        averages.add(Math.round((miscFootprintValue / recordsNum) * 1000) / 1000.0);
        averages.add(Math.round((totalFootprintValue / recordsNum) * 1000) / 1000.0);

        return averages;
    }
}
