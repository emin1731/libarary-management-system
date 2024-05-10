// UserAlreadyExistsException is an exception class that indicates when an attempt is made to register a user with a username that already exists in the system.
package exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
