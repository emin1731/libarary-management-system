package exceptions;

public class InvalidRatingException extends Exception{
    public InvalidRatingException(String errorMessage) {
        super(errorMessage);
    }
}
