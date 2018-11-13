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
    public static Task getTask(TaskType type, String description, TaskStatus status, String from, String until) throws TaskManagerException {
        switch(type){
            case TIMED:
                return new Timed(description, DateTime.stringToCalendar(from), DateTime.stringToCalendar(until), status);
            default:
                return getTask(type,description,status,until);
        }
    }

    public static Task getTask(TaskType type, String description, TaskStatus status, String from, String until, TaskPriority priority) throws TaskManagerException {
        Task task = getTask(type,description,status,from,until);
        task.setPriority(priority);
        return task;
    }

}
