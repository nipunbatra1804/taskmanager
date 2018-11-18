package parser;

import exceptions.InvalidCommandParameterException;
import exceptions.InvalidDateException;
import exceptions.TaskManagerException;
import exceptions.InvalidCommandFormatException;
import tasks.*;

import java.util.Calendar;

/**
 * Static class for parsing commands from user
 */
public class Interpreter {

    private static final String DT_DELIM_DEADLINE = "/by";
    private static final String DT_DELIM_TIMED1 = "/from";
    private static final String DT_DELIM_TIMED2 = "/to";

    private static final String CMD_DONE = "done";
    private static final String CMD_DELETE = "delete";

    /**
     * return full description from full command string
     * @param fullcommand  input string
     * @return command word
     */
    public static String getCommandDescription(String fullcommand) throws InvalidCommandFormatException{
        try{
        String description =  fullcommand.trim();
        description = description.split("[ ]", 2)[1];
        description = description.split("/", 2)[0];
        return description.trim();
        } catch (Exception ex) {
            throw new InvalidCommandFormatException("");
        }
    }

    /**
     * return command word from full command string
     * @param fullcommand  input string
     * @return command word
     */
    public static String getCommandWord(String fullcommand){
        String command =  fullcommand.trim();
        command = command.split(" ", 2)[0];
        return command.toLowerCase();
    }

    /**
     * return parameter immediately following a command
     * @param command command word
     * @param fullcommand full command entered by user
     * @return parameter following command
     */
    public static String getCommandParameter(String command, String fullcommand){
        String description = fullcommand.substring(fullcommand.indexOf(command)+command.length()).trim();
        return description.split("[ ]")[0];
    }

    /**
     * eturn parameter immediately following a command until end of a string
     * @param command command word
     * @param fullcommand full command entered by user
     * @return rest of string following the command word
     */
    public static String getCommandParamUntilEnd(String command, String fullcommand){
        String description = fullcommand.substring(fullcommand.indexOf(command)+command.length()).trim();
        return description;
    }

    /**
     * create a task of form todo from user input
     * @param fullcommand full command string entered by user
     * @return todo object of Task reference
     * @throws TaskManagerException
     */
    public static Task createTodo(String fullcommand) throws TaskManagerException{
        String description = getCommandDescription(fullcommand);
        if (description.isEmpty()) {
            throw new TaskManagerException("Empty Description for TODO");
        }
        return TaskFactory.getTask(TaskType.TODO, description, TaskStatus.OPEN, null,null, TaskPriority.getPriorityFrmCmd(fullcommand));
    }


    /**
     * create a task of deadline type from user input
     * @param fullcommand  full command string entered by user
     * @return Deadline object of reference Task type
     * @throws TaskManagerException
     */
    public static Task createDeadline(String fullcommand) throws TaskManagerException{
        if(!fullcommand.toLowerCase().contains(DT_DELIM_DEADLINE)){
            throw new InvalidCommandFormatException("Incorrect Format for createDeadline");
        }
        String task_description = getCommandDescription(fullcommand);
        String by = getDateTimeString(fullcommand,DT_DELIM_DEADLINE);
        if(task_description.isEmpty()){
            throw new InvalidCommandFormatException("Empty Description for createDeadline");
        }
        if(by.isEmpty()){
            throw new InvalidCommandFormatException("Empty by for createDeadline");
        }
        try {
            return TaskFactory.getTask(TaskType.DEADLINE, task_description, TaskStatus.OPEN, null, by, TaskPriority.getPriorityFrmCmd(fullcommand));
        } catch(InvalidDateException e){
            throw new InvalidDateException("Invalid Date Format");
        }
    }


    private static String getDateTimeString(String fullcommand, String start) {
        String timestr = fullcommand.substring(fullcommand.toLowerCase().indexOf(start)+start.length()).trim();
        return timestr;
    }


    private static String getDateTimeString(String fullcommand, String start, String end) throws InvalidCommandFormatException {
        try {
            String timestr = fullcommand.substring(fullcommand.toLowerCase().indexOf(start) + start.length(), fullcommand.toLowerCase().indexOf(end)).trim();
            return timestr;
        } catch (IndexOutOfBoundsException ex) {
            throw new InvalidCommandFormatException("");
        }

    }

    public static Task createTimed(String fullcommand) throws TaskManagerException {
        if(!fullcommand.toLowerCase().contains(DT_DELIM_TIMED1) || !fullcommand.toLowerCase().contains(DT_DELIM_TIMED2)){
            throw new TaskManagerException("Incorrect format for tasks.Timed");
        }
        String task_description = getCommandDescription(fullcommand);
        String from = getDateTimeString(fullcommand,DT_DELIM_TIMED1,DT_DELIM_TIMED2);
        String to = getDateTimeString(fullcommand,DT_DELIM_TIMED2);
        if(task_description.isEmpty()){
            throw new InvalidCommandFormatException("Empty description for tasks.Timed");
        }
        if(from.isEmpty()){
            throw new InvalidCommandFormatException("Empty from field for tasks.Timed");
        }
        if(to.isEmpty()){
            throw new InvalidCommandFormatException("Empty to field for tasks.Timed");
        }
        try {
            return TaskFactory.getTask(TaskType.TIMED,task_description,TaskStatus.OPEN,from,to, TaskPriority.getPriorityFrmCmd(fullcommand));
        } catch(InvalidDateException e){
            throw new InvalidDateException("Invalid Date Format");
        }
    }

    /**
     * get index for task to be marked as completed
     * @param line input command
     * @return index of task to be marked as completed
     */
    public static int getMarkTaskDoneIndex(String line) throws TaskManagerException{
        String lineLowercase = line.toLowerCase();
        return parseDouble(lineLowercase.substring(CMD_DONE.length()));
    }

    /**
     * get index of task to be dedleted from tasklist
     * @param line input command
     * @return index of task to be deleted
     */
    public static int getDeleteTaskIndex(String line) throws TaskManagerException{
        String lineLowercase = line.toLowerCase();
        return parseDouble(lineLowercase.substring(CMD_DELETE.length()));
    }

    /**
     * return index for a change task request
     * @param description input String containing index
     * @return index of task to be changed
     * @throws TaskManagerException if description is not parseable as a number
     */
    public static int getChangeTaskIndex(String description) throws TaskManagerException{
        try {
            return parseDouble(description);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandFormatException("Invalid change command format");
        }
    }

    private static int parseDouble(String description) throws TaskManagerException {
        try {
            return (int) Double.parseDouble(description.trim());
        } catch (IndexOutOfBoundsException e) {
            throw new TaskManagerException("invalid task index");
        }
    }

    /**
     *
     * @param description string to use to paarse and create a date
     * @return Calendar object
     * @throws InvalidDateException if date is not parseable
     * @throws InvalidCommandParameterException if command parameter for date is of incorrect format
     */
    public static Calendar createDate(String description)throws InvalidDateException, InvalidCommandParameterException {
        String datestr = description.trim();
        try {
            return DateTime.stringToCalendar(datestr);
        } catch (InvalidDateException e) {
            throw e;
        }

    }

}
