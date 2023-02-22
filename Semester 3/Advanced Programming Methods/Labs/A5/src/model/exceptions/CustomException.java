package model.exceptions;

public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Something went wrong: " + super.getMessage();
    }
}
