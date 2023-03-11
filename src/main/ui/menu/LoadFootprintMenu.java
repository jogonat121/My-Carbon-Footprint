package ui.menu;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import persistence.JsonReader;

import java.util.Scanner;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for loading footprint record from local file
public class LoadFootprintMenu extends Menu {
    private final Scanner input;

    // EFFECTS: constructs a menu with your footprint name;
    // throws CannotAccessDataException if error occurs while accessing data
    public LoadFootprintMenu() throws CannotAccessDataException {
        super("Your Footprint");
        input = new Scanner(System.in);
        runMenu();
    }

    // EFFECTS: loads the footprint record from JSON file and displays the footprint values;
    // throws CannotAccessDataException if error occurs while reading file;
    public void runMenu() throws CannotAccessDataException {
        System.out.println("Enter name of the file: ");
        String fileName = input.next();

        FootprintRecord footprintRecord = new JsonReader(fileName).read();
        Footprint foodFootprint = footprintRecord.getFoodFootprint();
        Footprint travelFootprint = footprintRecord.getTravelFootprint();
        Footprint miscFootprint = footprintRecord.getMiscFootprint();

        System.out.println("Your Food Footprint is: " + foodFootprint.getValue() + UNITS);
        System.out.println("Your Travel Footprint is: " + travelFootprint.getValue() + UNITS);
        System.out.println("Your Misc. Footprint is: " + miscFootprint.getValue() + UNITS);
        System.out.println("Your Total Footprint is: " + footprintRecord.getTotalValue() + UNITS);

        System.out.println("Do you want to edit these values? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            new EditFootprintMenu(footprintRecord);
            saveRecord(footprintRecord, fileName);
        }
    }

    // MODIFIES: file
    // EFFECTS: saves the edits to the JSON file
    private void saveRecord(FootprintRecord footprintRecord, String fileName) {
        System.out.println("\nDo you want to save your footprint record file with these values? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            try {
                footprintRecord.exportFile(fileName);
                System.out.println("Successfully saved to ./data/" + fileName + ".json");
            } catch (CannotAccessDataException e) {
                System.out.println("Cannot write data to file");
            }
        }
    }
}
