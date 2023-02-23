package ui;

import model.Footprint;
import model.Question;
import model.QuestionBank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyCarbonFootprint {
    private Scanner input;
    private QuestionBank travelQuestionBank;
    private QuestionBank foodQuestionBank;
    private QuestionBank miscQuestionBank;
    private static final String UNITS = " metric tonnes CO2";
    
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
        foodQuestionBank = new QuestionBank("Food");
        travelQuestionBank = new QuestionBank("Travel");
        miscQuestionBank = new QuestionBank("Misc.");

        try {
            Scanner questions = new Scanner(new File("./data/questions.csv"));
            questions.nextLine();
            while (questions.hasNext())  {
                String[] attributes = questions.nextLine().split("; ");
                Question question = new Question(attributes[0], Double.parseDouble(attributes[1]), attributes[2]);

                if (question.getCategory().equals("Food")) {
                    foodQuestionBank.addQuestion(question);
                } else if (question.getCategory().equals("Travel")) {
                    travelQuestionBank.addQuestion(question);
                } else {
                    miscQuestionBank.addQuestion(question);
                }
            }
            questions.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> calculate my footprint");
        System.out.println("\tq -> quit");
    }

    private void selectOption(String option) {
        if (option.equals("c")) {
            calculateFootprintMenu();
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void calculateFootprintMenu() {
        List<Double> foodValues = askQuestions(foodQuestionBank);
        List<Double> travelValues = askQuestions(travelQuestionBank);
        List<Double> miscValues = askQuestions(miscQuestionBank);

        Footprint travelFootprint = travelQuestionBank.calculateFootprint(travelValues);
        Footprint foodFootprint = foodQuestionBank.calculateFootprint(foodValues);
        Footprint miscFootprint = miscQuestionBank.calculateFootprint(miscValues);

        double travelFootprintValue = travelFootprint.getValue();
        double foodFootprintValue = foodFootprint.getValue();
        double miscFootprintValue = miscFootprint.getValue();
        double totalFootprintValue = travelFootprintValue + foodFootprintValue + miscFootprintValue;

        System.out.println("Your Travel Footprint is: " + travelFootprintValue + UNITS);
        System.out.println("Your Food Footprint is: " + foodFootprintValue + UNITS);
        System.out.println("Your Misc. Footprint is: " + miscFootprintValue + UNITS);
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
}
