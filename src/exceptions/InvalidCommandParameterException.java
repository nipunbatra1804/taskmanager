package exceptions;

/**
 * Exception for handling user command parameters of incorrect parameters
 */
public class InvalidCommandParameterException extends  TaskManagerException {
    public InvalidCommandParameterException(String message){
        super(message);
    }
}