package ui;

import data.exceptions.CannotAccessDataException;
import model.exceptions.EmptyQuestionBankException;
import ui.menu.AveragesMenu;
import ui.menu.CalculateFootprintMenu;
import ui.menu.DeleteRecordMenu;
import ui.menu.LoadFootprintMenu;

import java.util.Scanner;

// Represents the UI
public class MyCarbonFootprint {
    public static final String UNITS = " metric tonnes CO2 a year";

    // EFFECTS: constructs the UI and runs the app
    public MyCarbonFootprint() {
        runApp();
    }

    // EFFECTS: Initializes and displays the main options
    private void runApp() {
        boolean quit = false;
        String option;
        Scanner input = new Scanner(System.in);

        while (!quit) {
            displayMenu();
            option = input.next().toLowerCase();

            if (option.equals("q")) {
                quit = true;
            } else {
                selectOption(option);
            }
        }

        System.out.println("Thank you for using My Carbon Footprint :)\nHave a nice day, bye for now!");
    }

    // EFFECTS: displays the options (or features/functionality)
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> calculate my footprint");
        System.out.println("\ta -> check average footprint");
        System.out.println("\tl -> load footprint from file");
        System.out.println("\td -> delete my record");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: selects and runs the appropriate menu
    private void selectOption(String option) {
        try {
            switch (option) {
                case "c":
                    new CalculateFootprintMenu();
                    break;
                case "d":
                    new DeleteRecordMenu();
                    break;
                case "a":
                    new AveragesMenu();
                    break;
                case "l":
                    new LoadFootprintMenu();
                    break;
                default:
                    System.out.println("Invalid option specified\nPlease enter a valid option");
                    break;
            }
        } catch (CannotAccessDataException | EmptyQuestionBankException e) {
            System.out.println(e.getMessage());
            runApp();
        }
    }
}
