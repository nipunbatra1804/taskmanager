package tasks;

import exceptions.TaskManagerException;
import parser.DateTime;
/**
 * Static Factory class that creates all Task objects in the program
 */
public class TaskFactory {

    /**
     * Factory for Task objects
     * @param type type of subclass to Spawn
     * @param description description of Task object
     * @param status status of the spawned task
     * @param by deadline for the Deadline Tasks,
     * @return created Task object
     * @throws TaskManagerException
     */
    public static Task getTask(TaskType type, String description, TaskStatus status, String by) throws TaskManagerException {
        switch(type){
            case TODO:
                return new Todo(description, status);
            case DEADLINE:
                return new Deadline(description, DateTime.stringToCalendar(by), status);
            default:
                break;
        }
        return null;
    }
}
