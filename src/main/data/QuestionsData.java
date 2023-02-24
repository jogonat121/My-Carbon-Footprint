package data;

import data.exceptions.CannotAccessDataException;
import model.Question;
import model.QuestionBank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents questions' data with path to CSV data file, and question banks
public class QuestionsData {
    private final String path;
    public static final String PATH = "./data/questions.csv";
    private final QuestionBank foodQuestionBank;
    private final QuestionBank travelQuestionBank;
    private final QuestionBank miscQuestionBank;

    // EFFECTS: constructs a questions' data with path to CSV file, and new food, travel and misc. question banks
    public QuestionsData() {
        path = PATH;
        foodQuestionBank = new QuestionBank("Food");
        travelQuestionBank = new QuestionBank("Travel");
        miscQuestionBank = new QuestionBank("Misc.");
    }

    // EFFECTS: returns this.path
    public String getPath() {
        return path;
    }

    // EFFECTS: returns the food question bank
    public QuestionBank getFoodQuestionBank() {
        return foodQuestionBank;
    }

    // EFFECTS: returns the travel question bank
    public QuestionBank getTravelQuestionBank() {
        return travelQuestionBank;
    }

    // EFFECTS: returns the misc. question bank
    public QuestionBank getMiscQuestionBank() {
        return miscQuestionBank;
    }

    // MODIFIES: this
    // EFFECTS: constructs and loads the questions from the file at the given path to the appropriate question banks
    public List<QuestionBank> loadQuestions(String pathName) throws CannotAccessDataException {
        List<QuestionBank> questionBanks = new ArrayList<>();
        questionBanks.add(foodQuestionBank);
        questionBanks.add(travelQuestionBank);
        questionBanks.add(miscQuestionBank);

        try {
            Scanner questions = new Scanner(new File(pathName));
            questions.nextLine();
            while (questions.hasNext()) {
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
        } catch (Exception e) {
            throw new CannotAccessDataException("Cannot read data from file");
        }

        return questionBanks;
    }

    // MODIFIES: this
    // EFFECTS: constructs and loads the questions from the file to the appropriate question banks
    public List<QuestionBank> loadQuestions() throws CannotAccessDataException {
        return loadQuestions(path);
    }
}
