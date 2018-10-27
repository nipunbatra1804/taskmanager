package exceptions;

/**
 * Taskmanager exception to handle user-expected exceptions
 */
public class TaskManagerException extends Exception {
    public TaskManagerException(String message) {
        super(message);
    }
}
