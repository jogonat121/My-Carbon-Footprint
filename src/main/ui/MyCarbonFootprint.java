package ui;

import data.FootprintRecord;
import data.QuestionsData;
import data.UserRecords;
import model.Footprint;
import model.Question;
import model.QuestionBank;

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
        questionBanks = questionsData.loadQuestions();

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
        List<Double> foodValues = askQuestions(questionBanks.get(0));
        List<Double> travelValues = askQuestions(questionBanks.get(1));
        List<Double> miscValues = askQuestions(questionBanks.get(2));

        Footprint foodFootprint = questionBanks.get(0).calculateFootprint(foodValues);
        Footprint travelFootprint = questionBanks.get(1).calculateFootprint(travelValues);
        Footprint miscFootprint = questionBanks.get(2).calculateFootprint(miscValues);

        double travelFootprintValue = travelFootprint.getValue();
        double foodFootprintValue = foodFootprint.getValue();
        double miscFootprintValue = miscFootprint.getValue();
        double totalFootprintValue = travelFootprintValue + foodFootprintValue + miscFootprintValue;
        double totalRounded = Math.round((totalFootprintValue * 1000) / 1000.0);

        System.out.println("Your Travel Footprint is: " + travelFootprintValue + UNITS);
        System.out.println("Your Food Footprint is: " + foodFootprintValue + UNITS);
        System.out.println("Your Misc. Footprint is: " + miscFootprintValue + UNITS);
        System.out.println("Your Total Footprint is: " + totalRounded + UNITS);

        checkAverages();

        System.out.println("\nDo you want to contribute your footprint record to our data? (Enter y for yes)");
        if (input.next().equalsIgnoreCase("y")) {
            saveRecord(foodFootprint, travelFootprint, miscFootprint);
        }
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
        String uniqueID = UUID.randomUUID().toString();
        FootprintRecord footprintRecord = new FootprintRecord(uniqueID, food, travel, misc);
        if (footprintRecord.saveData()) {
            System.out.println("Successfully recorded!");
            System.out.println("Please remember the unique ID for your record which is " + uniqueID);
        }

    }

    private void deleteRecord() {
        System.out.println("Enter unique ID of the record to be deleted");
        UserRecords userRecords = new UserRecords();
        userRecords.removeRecord(input.next());
    }

    private void checkAverages() {
        UserRecords userRecords = new UserRecords();
        List<Double> averages = userRecords.getAverages();

        System.out.println("\nThe Average Travel Footprint is: " + averages.get(0) + UNITS);
        System.out.println("The Average Food Footprint is: " + averages.get(1) + UNITS);
        System.out.println("The Average Misc. Footprint is: " + averages.get(2) + UNITS);
        System.out.println("The Average Total Footprint is: " + averages.get(3) + UNITS);
    }
}
