package exceptions;

public class InvalidCommandParameterException extends  TaskManagerException {
    public InvalidCommandParameterException(String message){
        super(message);
    }
}