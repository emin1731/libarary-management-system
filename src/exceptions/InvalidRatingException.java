// InvalidRatingException is an exception class that indicates when an invalid rating value is encountered, such as a negative rating or a rating exceeding the allowed range.
package exceptions;

public class InvalidRatingException extends Exception{
    public InvalidRatingException(String errorMessage) {
        super(errorMessage);
    }
}
