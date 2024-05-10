// UserNotFoundException is an exception class used to signify when a requested user is not found in the system.
package exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
