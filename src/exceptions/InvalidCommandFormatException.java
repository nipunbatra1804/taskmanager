package exceptions;

public class InvalidCommandFormatException extends TaskManagerException {
    public InvalidCommandFormatException(String message){
        super(message);
    }
}

