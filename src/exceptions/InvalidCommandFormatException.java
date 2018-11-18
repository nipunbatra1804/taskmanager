package exceptions;

/**
 * Exception for handling use commands of incorrect format
 */
public class InvalidCommandFormatException extends TaskManagerException {
    public InvalidCommandFormatException(String message){
        super(message);
    }
}

