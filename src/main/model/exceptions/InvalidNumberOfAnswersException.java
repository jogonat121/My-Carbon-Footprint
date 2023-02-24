package model.exceptions;

public class InvalidNumberOfAnswersException extends Exception {
    private final int size;

    public InvalidNumberOfAnswersException(int size) {
        super(size + " is not equal to the number of questions");
        this.size = size;
    }

    // getter
    public int getSize() {
        return size;
    }
}
