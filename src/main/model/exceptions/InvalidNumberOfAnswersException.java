package model.exceptions;

public class InvalidNumberOfAnswersException extends Exception {
    public InvalidNumberOfAnswersException(int size) {
        super(size + " is not equal to the number of questions");
    }
}
