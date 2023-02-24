package model.exceptions;

public class EmptyQuestionBankException extends Exception {
    public EmptyQuestionBankException() {
        super("Question bank is empty");
    }
}
