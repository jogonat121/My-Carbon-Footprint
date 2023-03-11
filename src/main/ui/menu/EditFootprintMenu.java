package ui.menu;

import data.FootprintRecord;
import model.Footprint;

import java.util.Scanner;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for editing record
public class EditFootprintMenu extends Menu {
    private final Scanner input;

    // EFFECTS: constructs a menu with edit footprint name
    public EditFootprintMenu(FootprintRecord fr) {
        super("Edit Footprint");
        input = new Scanner(System.in);
        runMenu(fr);
    }

    // MODIFIES: footprintRecord
    // EFFECTS: edits the footprint record and displays the new footprint values
    public void runMenu(FootprintRecord footprintRecord) {
        Footprint foodFootprint = footprintRecord.getFoodFootprint();
        Footprint travelFootprint = footprintRecord.getTravelFootprint();
        Footprint miscFootprint = footprintRecord.getMiscFootprint();

        System.out.println("Enter new value of Food footprint: ");
        foodFootprint.setValue(Double.parseDouble(input.next()));
        System.out.println("Enter new value of Travel footprint: ");
        travelFootprint.setValue(Double.parseDouble(input.next()));
        System.out.println("Enter new value of Misc. footprint: ");
        miscFootprint.setValue(Double.parseDouble(input.next()));
        System.out.println("Successfully edited! ");

        System.out.println("Your Food Footprint is: " + foodFootprint.getValue() + UNITS);
        System.out.println("Your Travel Footprint is: " + travelFootprint.getValue() + UNITS);
        System.out.println("Your Misc. Footprint is: " + miscFootprint.getValue() + UNITS);
        System.out.println("Your Total Footprint is: " + footprintRecord.getTotalValue() + UNITS);
    }
}
