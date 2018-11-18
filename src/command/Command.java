package command;

import exceptions.InvalidCommandFormatException;

import exceptions.InvalidCommandParameterException;
import exceptions.TaskManagerException;
import parser.Interpreter;
import tasklist.TaskList;
import tasks.Task;
import tasks.TaskPriority;

import java.util.List;

/**
 * Static class to execute commands entered from the user
 */
public class Command {

    /**
     * Command Type Keywords:
     */
    private static final String EXIT = "exit";
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String TIMED = "timed";
    private static final String PRINT = "print";
    private static final String DONE = "done";
    private static final String DELETE = "delete";
    private static final String COMPLETED = "completed";
    private static final String INCOMPLETE = "incomplete";
    private static final String CHANGE = "change";
    private static final String SHOW = "show";


    private static final String PRIORITY = "priority";
    private static final String DUE_TODAY= "today";
    private static final String DESCRIPTION = "description";


    /**
     * Execute show commands
     * @param tasks tasklist object to show details from
     * @param command command entered by the user
     * @param description description of the user command
     * @return Feedback object to calling  class.
     * @throws InvalidCommandFormatException
     * @throws InvalidCommandParameterException
     */
    private static Feedback executeShowCommand(TaskList tasks, String command,String description) throws InvalidCommandFormatException, InvalidCommandParameterException {
        switch(command){
            case PRIORITY:
                String searchpriority =  Interpreter.getCommandParameter(command,description);
                List<Task> ofpriority = tasks.getPriority(searchpriority);
                return new Feedback(tasks.printTasks(ofpriority));
            case COMPLETED:
                List<Task> completed = tasks.getCompleted();
                return new Feedback(tasks.printTasks(completed));
            case INCOMPLETE:
                List<Task> incomplete = tasks.getIncomplete();
                return new Feedback(tasks.printTasks(incomplete));
            case DUE_TODAY:
                List<Task> duetoday = tasks.getDueToday();
                return new Feedback(tasks.printTasks(duetoday));
            default:return new Feedback("Unknown show command parameter");

        }

    }

    /**
     * exectute subcommands involving the change of particular tasks
     * @param tasks
     * @param command
     * @param description
     * @return
     * @throws TaskManagerException
     */
    private static Feedback executeChangeCommand(TaskList tasks, String command,String description) throws TaskManagerException {
        int taskId;
        switch(command){
            case PRIORITY:
                taskId  =  Interpreter.getChangeTaskIndex(Interpreter.getCommandParameter(command,description));
                String priority = Interpreter.getCommandParamUntilEnd(Integer.toString(taskId),description);
                tasks.changePriority(taskId, TaskPriority.getPriorityFrmDesc(priority));
                break;
            case DEADLINE:
                taskId = Interpreter.getChangeTaskIndex(Interpreter.getCommandParameter(command,description));
                String datestr = Interpreter.getCommandParamUntilEnd(Integer.toString(taskId),description);
                tasks.changeDate(taskId, Interpreter.createDate(datestr));
                break;
            case DESCRIPTION:
                taskId = Interpreter.getChangeTaskIndex(Interpreter.getCommandParameter(command,description));
                String taskDesc = Interpreter.getCommandParamUntilEnd(Integer.toString(taskId),description);
                tasks.changeDesc(taskId,taskDesc);
                break;

            default:return new Feedback("Unknown change command parameter");

        }
        return new Feedback("");
    }
    /**
     *
     * @param isExit
     * @param command word to specify what command user wants the Task Manager should execute
     * @param  userCommand specifies the usercommand used by parser class
     * @return iExit falg which states user wants to exit
     * @throws TaskManagerException with feedback for user
     */
    public static Feedback executeCommand(TaskList tasks, boolean isExit, String command, String userCommand) throws TaskManagerException {
        String subcommand;
        String message;
        switch (command) {
            case EXIT:
            case "": // exit if user input is empty
                isExit = true;
                break;
            case TODO:
                Task todo = Interpreter.createTodo(userCommand);
                tasks.addTask(todo);
                break;
            case DEADLINE:
                Task deadline =  Interpreter.createDeadline(userCommand);
                tasks.addTask(deadline);
                break;
            case TIMED:
                Task timed = Interpreter.createTimed(userCommand);
                tasks.addTask(timed);
                break;
            case PRINT:
                tasks.printTasks();
                break;
            case DONE:
                int doneindex = Interpreter.getMarkTaskDoneIndex(userCommand);
                tasks.markAsDone(doneindex);
                break;
            case DELETE:
                int deleteindex = Interpreter.getDeleteTaskIndex(userCommand);
                tasks.deleteTask(deleteindex);
                break;
            case SHOW:
                subcommand = Interpreter.getCommandWord(Interpreter.getCommandDescription(userCommand));
                return executeShowCommand(tasks,subcommand, Interpreter.getCommandDescription(userCommand));
            case CHANGE:
                subcommand = Interpreter.getCommandWord(Interpreter.getCommandDescription(userCommand));
                return executeChangeCommand(tasks, subcommand, Interpreter.getCommandDescription(userCommand));
            default:
                return new Feedback("Unknown Command Entered");
        }
        return new Feedback(isExit);
    }


}
