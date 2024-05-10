// EmptyUsernameOrPasswordException is an exception class that signals when a username or password is empty during user registration or login processes.
package exceptions;

public class EmptyUsernameOrPasswordException extends Exception{
    public EmptyUsernameOrPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
