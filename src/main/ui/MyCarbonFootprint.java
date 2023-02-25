package ui;

import data.exceptions.CannotAccessDataException;
import model.exceptions.EmptyQuestionBankException;
import ui.menu.AveragesMenu;
import ui.menu.CalculateFootprintMenu;
import ui.menu.DeleteRecordMenu;

import java.util.Scanner;

public class MyCarbonFootprint {
    public static final String UNITS = " metric tonnes CO2 a year";

    public MyCarbonFootprint() {
        runApp();
    }

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

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> calculate my footprint");
        System.out.println("\ta -> check average footprint");
        System.out.println("\td -> delete my record");
        System.out.println("\tq -> quit");
    }

    private void selectOption(String option) {
        try {
            if (option.equals("c")) {
                new CalculateFootprintMenu();
            } else if (option.equals("d")) {
                new DeleteRecordMenu();
            } else if (option.equals("a")) {
                new AveragesMenu();
            } else {
                System.out.println("Invalid option.");
            }
        } catch (CannotAccessDataException e) {
            System.out.println(e.getMessage());
            runApp();
        } catch (EmptyQuestionBankException e) {
            System.out.println(e.getMessage());
            System.out.print("Sorry no questions available");
            runApp();
        }
    }
}
