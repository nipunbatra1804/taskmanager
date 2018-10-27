package parser;

import exceptions.InvalidDateException;
import exceptions.TaskManagerException;
import tasks.Task;
import tasks.TaskFactory;
import tasks.TaskStatus;
import tasks.TaskType;

/**
 * Static class for parsing commands from user
 */
public class Parser {
    /**
     * return command word from full command string
     * @param fullcommand  input string
     * @return command word
     */
    public static String getCommandWord(String fullcommand){
        String command =  fullcommand.trim();
        command = command.split(" ", 2)[0];
        return command;
    }

    /**
     * create a task of form todo from user input
     * @param fullcommand
     * @return todo object of Task reference
     * @throws TaskManagerException
     */
    public static Task createTodo(String fullcommand) throws TaskManagerException{
        String description = fullcommand.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new TaskManagerException("Empty Description for TODO");
        }
        return TaskFactory.getTask(TaskType.TODO, description, TaskStatus.OPEN, null);
    }

    /**
     * create a task of deadline type from user input
     * @param fullcommand
     * @return Deadline object of reference Task type
     * @throws TaskManagerException
     */
    public static Task createDeadline(String fullcommand) throws TaskManagerException{
        String description = fullcommand.substring("deadline".length()).trim();
        if(!description.contains("/by")){
            throw new TaskManagerException("Incorrect Format for tasks.Deadline");
        }
        String task_description = description.substring(0,description.indexOf("by")).trim();
        String by = description.substring(description.indexOf("/by")+"/by".length()).trim();
        if(task_description.isEmpty()){
            throw new TaskManagerException("Empty Description for tasks.Deadline");
        }
        if(by.isEmpty()){
            throw new TaskManagerException("Empty deadlline" +
                    "ls for tasks.Deadline");
        }
        try {
            return TaskFactory.getTask(TaskType.DEADLINE, task_description, TaskStatus.OPEN, by);
        } catch(InvalidDateException e){
            throw new InvalidDateException("Invalid Date Format");
        }
    }

    /**
     * get index for task to be marked as completed
     * @param line input command
     * @return index of task to be marked as completed
     */
    public static int getMarkTaskDoneIndex(String line){
        return (int)Double.parseDouble(line.substring("done".length()).trim());
    }

    /**
     * get index of task to be dedleted from tasklist
     * @param line input command
     * @return index of task to be deleted
     */
    public static int getDeleteTaskDoneIndex(String line){
        return (int)Double.parseDouble(line.substring("delete".length()).trim());
    }

}
