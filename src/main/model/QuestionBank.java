package model;

import java.util.LinkedList;
import java.util.List;

// Represents a question bank with questions of a particular category
public class QuestionBank {
    private final LinkedList<Question> questionBank;
    private final String category;

    // EFFECTS: constructs a question bank with no questions and with the given category
    public QuestionBank(String category) {
        this.questionBank = new LinkedList<>();
        this.category = category;
    }

    // EFFECTS: returns the category
    public String getCategory() {
        return category;
    }

    // EFFECTS: returns the list of questions in the question bank
    public List<Question> getQuestionBank() {
        return questionBank;
    }

    // EFFECTS: returns the number of questions in the question bank
    public int length() {
        return (questionBank.size());
    }

    // REQUIRES: question bank is not empty
    // MODIFIES: this
    // EFFECTS: removes the question bank at the front of the queue and returns it
    public Question getNextQuestion() {
        return questionBank.removeFirst();
    }

    // EFFECTS: returns true if question bank is empty, false otherwise
    public boolean isEmpty() {
        return (questionBank.size() == 0);
    }

    // MODIFIES: this
    // EFFECTS: adds a question to the question bank
    public void addQuestion(Question q) {
        this.questionBank.add(q);
    }

    // REQUIRES: questionBank.size == usrValues.size()
    // MODIFIES: this
    // EFFECTS: calculates the footprint with the corresponding user values for each question
    //  and remove those questions
    public Footprint calculateFootprint(List<Double> usrValues) {
        int valIndex = 0;
        Footprint footprint = new Footprint(category);

        try {
            while (!isEmpty()) {
                double usrUsage = usrValues.get(valIndex);
                Question question = getNextQuestion();

                double footprintValue = question.calculateFootprint(usrUsage);
                footprint.addValue(footprintValue);
                valIndex++;
            }
        } catch (NullPointerException e) {
            System.out.println("Load questions into question bank first!");
        }

        return footprint;
    }
}
