package exceptions;

/**
 * exception for handling exceptions relating to invalid time formats
 */
public class InvalidDateException extends TaskManagerException {
    public InvalidDateException(String message){
        super(message);
    }
}
