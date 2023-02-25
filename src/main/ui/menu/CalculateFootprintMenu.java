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

    // EFFECTS: constructs a menu with calculate footprint name
    public CalculateFootprintMenu()
            throws CannotAccessDataException, EmptyQuestionBankException {
        super("Calculate Footprint");
        init();
    }

    // EFFECTS: loads the questions from data and runs the menu
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

    // EFFECTS: calculates and displays the user footprint
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
                {foodFootprint.getValue(), travelFootprint.getValue(), miscFootprint.getValue()};
        displayFootprint(footprintValues);

        new AveragesMenu();
        saveRecord(foodFootprint, travelFootprint, miscFootprint);

    }

    // EFFECTS: displays the footprint values
    private void displayFootprint(Double[] values) {
        double totalFootprintValue = Math.round((values[0] + values[1] + values[2]) * 1000) / 1000.0;

        System.out.println("Your Travel Footprint is: " + values[0] + UNITS);
        System.out.println("Your Food Footprint is: " + values[1] + UNITS);
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
    // EFFECTS: adds the footprint record to user records data file, and returns the unique ID of that record
    private void saveRecord(Footprint food, Footprint travel, Footprint misc) throws CannotAccessDataException {
        System.out.println("\nDo you want to contribute your footprint record to our data? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            String uniqueID = UUID.randomUUID().toString();
            FootprintRecord footprintRecord = new FootprintRecord(uniqueID, food, travel, misc);

            if (footprintRecord.saveData()) {
                System.out.println("Successfully recorded!");
                System.out.println("Please remember the unique ID for your record which is " + uniqueID);
            }
        }
    }
}
