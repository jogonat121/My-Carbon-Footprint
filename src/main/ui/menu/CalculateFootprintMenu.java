package ui.menu;

import data.FootprintRecord;
import data.QuestionsData;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import model.Question;
import model.QuestionBank;
import model.exceptions.EmptyQuestionBankException;
import model.exceptions.InvalidNumberOfAnswersException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for calculating footprint
public class CalculateFootprintMenu extends Menu {
    private Scanner input;
    private List<QuestionBank> questionBanks;

    // EFFECTS: constructs a menu with calculate footprint name;
    // throws CannotAccessDataException if error occurs while accessing data;
    // throws EmptyQuestionBankException if there are no questions to load
    public CalculateFootprintMenu()
            throws CannotAccessDataException, EmptyQuestionBankException {
        super("Calculate Footprint");
        init();
    }

    // EFFECTS: loads the questions from data and runs the menu;
    // throws CannotAccessDataException if error occurs while reading data;
    // throws EmptyQuestionBankException if there are no questions to load
    private void init() throws CannotAccessDataException, EmptyQuestionBankException {
        try {
            QuestionsData questionsData = new QuestionsData();
            input = new Scanner(System.in);
            questionBanks = questionsData.loadQuestions();
            runMenu();
        } catch (InvalidNumberOfAnswersException e) {
            System.out.println(e.getMessage());
            init();
        }
    }

    // EFFECTS: calculates and displays the user footprint;
    // throws InvalidNumberOfAnswersException if no. of answers > no. of questions;
    // throws EmptyQuestionBankException if there are 0 questions in question bank;
    // throws CannotAccessDataException if error occurs while accessing data;
    private void runMenu()
            throws InvalidNumberOfAnswersException, EmptyQuestionBankException, CannotAccessDataException {
        QuestionBank foodQuestionBank = questionBanks.get(0);
        QuestionBank travelQuestionBank = questionBanks.get(1);
        QuestionBank miscQuestionBank = questionBanks.get(2);

        List<Double> foodValues = askQuestions(foodQuestionBank);
        List<Double> travelValues = askQuestions(travelQuestionBank);
        List<Double> miscValues = askQuestions(miscQuestionBank);
        Footprint foodFootprint = foodQuestionBank.calculateFootprint(foodValues);
        Footprint travelFootprint = travelQuestionBank.calculateFootprint(travelValues);
        Footprint miscFootprint = miscQuestionBank.calculateFootprint(miscValues);
        Double[] footprintValues = new Double[]
                {foodFootprint.getValue(), travelFootprint.getValue(), miscFootprint.getValue(),};
        displayFootprint(footprintValues);

        String uniqueID = UUID.randomUUID().toString();
        FootprintRecord footprintRecord = new FootprintRecord(uniqueID, foodFootprint, travelFootprint, miscFootprint);
        System.out.println("Do you want to edit these values? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            new EditFootprintMenu(footprintRecord);
        }

        new AveragesMenu();
        saveRecord(footprintRecord);
        exportRecord(footprintRecord);
    }

    // EFFECTS: displays the footprint values
    private void displayFootprint(Double[] values) {
        double totalFootprintValue = Math.round((values[0] + values[1] + values[2]) * 1000) / 1000.0;

        System.out.println("Your Food Footprint is: " + values[0] + UNITS);
        System.out.println("Your Travel Footprint is: " + values[1] + UNITS);
        System.out.println("Your Misc. Footprint is: " + values[2] + UNITS);
        System.out.println("Your Total Footprint is: " + totalFootprintValue + UNITS);
    }

    // EFFECTS: asks the questions in the given question bank
    private List<Double> askQuestions(QuestionBank qb) {
        List<Double> usrValues = new ArrayList<>();
        for (Question question : qb.getQuestionBank()) {
            System.out.println(question.getQuery());
            double ansUsage = Double.parseDouble(input.next());
            usrValues.add(ansUsage);
        }
        return usrValues;
    }

    // MODIFIES: data
    // EFFECTS: adds the footprint record to user records data file, and returns the unique ID of that record;
    // throws CannotAccessDataException if error occurs while writing data;
    private void saveRecord(FootprintRecord footprintRecord) throws CannotAccessDataException {
        System.out.println("\nDo you want to contribute your footprint record to our data? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            if (footprintRecord.saveData()) {
                System.out.println("Successfully recorded!");
                System.out.println("Please remember the unique ID for your record which is " + footprintRecord.getId());
            }
        }
    }

    // MODIFIES: data
    // EFFECTS: exports the footprint record as a JSON file
    private void exportRecord(FootprintRecord footprintRecord) {
        System.out.println("\nDo you want to save and export your footprint record to a file? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {

            System.out.println("Enter name of the file to save as: ");
            String fileName = input.next();
            try {
                footprintRecord.exportFile(fileName);
                System.out.println("Successfully saved to ./data/" + fileName + ".json");
            } catch (CannotAccessDataException e) {
                System.out.println("Cannot write data to file");
            }
        }
    }
}
