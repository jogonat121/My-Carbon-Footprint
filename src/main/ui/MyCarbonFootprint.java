package ui;

import data.exceptions.CannotAccessDataException;
import data.FootprintRecord;
import data.QuestionsData;
import data.UserRecords;
import data.exceptions.RecordNotFoundException;
import model.Footprint;
import model.Question;
import model.QuestionBank;
import model.exceptions.EmptyQuestionBankException;
import model.exceptions.InvalidNumberOfAnswersException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MyCarbonFootprint {
    private Scanner input;
    private static final String UNITS = " metric tonnes CO2 a year";
    private List<QuestionBank> questionBanks;
    
    public MyCarbonFootprint() {
        runApp();
    }

    private void runApp() {
        boolean quit = false;
        String option;

        init();

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

    private void init() {
        input = new Scanner(System.in);
        QuestionsData questionsData = new QuestionsData();
        try {
            questionBanks = questionsData.loadQuestions();
        } catch (CannotAccessDataException e) {
            System.out.println(e.getMessage());
        }

    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> calculate my footprint");
        System.out.println("\ta -> check average footprint");
        System.out.println("\td -> delete my record");
        System.out.println("\tq -> quit");
    }

    private void selectOption(String option) {
        if (option.equals("c")) {
            init();
            calculateFootprintMenu(questionBanks);
        } else if (option.equals("d")) {
            deleteRecord();
        } else if (option.equals("a")) {
            checkAverages();
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void calculateFootprintMenu(List<QuestionBank> questionBanks) {
        QuestionBank foodQuestionBank = questionBanks.get(0);
        QuestionBank travelQuestionBank = questionBanks.get(1);
        QuestionBank miscQuestionBank = questionBanks.get(2);
        List<Double> foodValues = askQuestions(foodQuestionBank);
        List<Double> travelValues = askQuestions(travelQuestionBank);
        List<Double> miscValues = askQuestions(miscQuestionBank);
        try {
            Footprint foodFootprint = foodQuestionBank.calculateFootprint(foodValues);
            Footprint travelFootprint = travelQuestionBank.calculateFootprint(travelValues);
            Footprint miscFootprint = miscQuestionBank.calculateFootprint(miscValues);
            Double[] footprintValues = new Double[]
                    {foodFootprint.getValue(), travelFootprint.getValue(), miscFootprint.getValue()};
            displayFootprint(footprintValues);

            checkAverages();
            saveRecord(foodFootprint, travelFootprint, miscFootprint);
        } catch (InvalidNumberOfAnswersException e) {
            System.out.println(e.getMessage());
            calculateFootprintMenu(questionBanks);
        } catch (EmptyQuestionBankException e) {
            System.out.println(e.getMessage());
            displayMenu();
        }
    }

    public void displayFootprint(Double[] values) {
        double totalFootprintValue = Math.round((values[0] + values[1] + values[2]) * 1000) / 1000.0;

        System.out.println("Your Travel Footprint is: " + values[0] + UNITS);
        System.out.println("Your Food Footprint is: " + values[1] + UNITS);
        System.out.println("Your Misc. Footprint is: " + values[2] + UNITS);
        System.out.println("Your Total Footprint is: " + totalFootprintValue + UNITS);
    }

    private List<Double> askQuestions(QuestionBank qb) {
        List<Double> usrValues = new ArrayList<>();

        try {
            for (Question question : qb.getQuestionBank()) {
                System.out.println(question.getQuery());
                double ansUsage = Double.parseDouble(input.next());
                usrValues.add(ansUsage);
            }
        } catch (NullPointerException e) {
            System.out.println("Load questions into question bank first!");
        }

        return usrValues;
    }

    private void saveRecord(Footprint food, Footprint travel, Footprint misc) {
        System.out.println("\nDo you want to contribute your footprint record to our data? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            String uniqueID = UUID.randomUUID().toString();
            FootprintRecord footprintRecord = new FootprintRecord(uniqueID, food, travel, misc);
            try {
                if (footprintRecord.saveData()) {
                    System.out.println("Successfully recorded!");
                    System.out.println("Please remember the unique ID for your record which is " + uniqueID);
                }
            } catch (CannotAccessDataException e) {
                System.out.println(e.getMessage());
                displayMenu();
            }
        }
    }

    private void deleteRecord() {
        System.out.println("Enter unique ID of the record to be deleted");

        UserRecords userRecords;
        try {
            userRecords = new UserRecords();
            userRecords.removeRecord(input.next());
        } catch (CannotAccessDataException e) {
            System.out.println(e.getMessage());
            displayMenu();
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            deleteRecord();
        }
    }

    private void checkAverages() {
        UserRecords userRecords;
        try {
            userRecords = new UserRecords();
            List<Double> averages = userRecords.getAverages();

            System.out.println("\nThe Average Travel Footprint is: " + averages.get(0) + UNITS);
            System.out.println("The Average Food Footprint is: " + averages.get(1) + UNITS);
            System.out.println("The Average Misc. Footprint is: " + averages.get(2) + UNITS);
            System.out.println("The Average Total Footprint is: " + averages.get(3) + UNITS);
        } catch (CannotAccessDataException e) {
            System.out.println(e.getMessage());
            displayMenu();
        }
    }
}
