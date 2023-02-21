package model;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private final List<Question> questionBank;

    public QuestionBank() {
        this.questionBank = new ArrayList<>();
    }

    public boolean addQuestion(Question q) {
        try {
            this.questionBank.add(q);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
