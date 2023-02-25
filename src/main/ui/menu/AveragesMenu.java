package ui.menu;

import data.UserRecords;
import data.exceptions.CannotAccessDataException;

import java.util.List;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for averages
public class AveragesMenu extends Menu {

    // EFFECTS: constructs a menu with averages name
    public AveragesMenu() throws CannotAccessDataException {
        super("Averages");
        runMenu();
    }

    // EFFECTS: computes and displays the average of each variable in user records
    private void runMenu() throws CannotAccessDataException {
        UserRecords userRecords;
        userRecords = new UserRecords();
        List<Double> averages = userRecords.getAverages();

        System.out.println("\nThe Average Travel Footprint is: " + averages.get(0) + UNITS);
        System.out.println("The Average Food Footprint is: " + averages.get(1) + UNITS);
        System.out.println("The Average Misc. Footprint is: " + averages.get(2) + UNITS);
        System.out.println("The Average Total Footprint is: " + averages.get(3) + UNITS);
    }
}
