package exceptions;

public class EmptyUsernameOrPasswordException extends Exception{
    public EmptyUsernameOrPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
