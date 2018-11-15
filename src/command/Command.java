package command;

import exceptions.InvalidCommandFormatException;

import exceptions.InvalidCommandParameterException;
import exceptions.TaskManagerException;
import parser.Parser;
import tasklist.TaskList;
import tasks.Task;
import tasks.TaskPriority;

import java.util.List;

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
     * Instance variables/objects
     */
    private static Feedback executeShowCommand(TaskList tasks, String command,String description) throws InvalidCommandFormatException, InvalidCommandParameterException {
        switch(command){
            case PRIORITY:
                String searchpriority =  Parser.getCommandParameter(command,description);
                List<Task> ofpriority = tasks.getPriority(searchpriority);
                tasks.printTasks(ofpriority);
            case COMPLETED:
                List<Task> completed = tasks.getCompleted();
                tasks.printTasks(completed);
                break;
            case INCOMPLETE:
                List<Task> incomplete = tasks.getIncomplete();
                tasks.printTasks(incomplete);
                break;
            case DUE_TODAY:
                List<Task> duetoday = tasks.getDueToday();
                tasks.printTasks(duetoday);
                break;
            default:return new Feedback("Unknown show command parameter");

        }
        return new Feedback("");
    }


    private static Feedback executeChangeCommand(TaskList tasks, String command,String description) throws TaskManagerException {
        int taskId;
        switch(command){
            case PRIORITY:
                taskId  =  Parser.getChangeTaskIndex(Parser.getCommandParameter(command,description));
                String priority = Parser.getCommandParameter(Integer.toString(taskId),description);
                tasks.changePriority(taskId, TaskPriority.getPriorityFrmDesc(priority));
                break;
            case DEADLINE:
                taskId = Parser.getChangeTaskIndex(Parser.getCommandParameter(command,description));
                String datestr = Parser.getEndCommandParameter(Integer.toString(taskId),description);
                tasks.changeDate(taskId,Parser.createDate(datestr));
                break;
            case DESCRIPTION:
                taskId = Parser.getChangeTaskIndex(Parser.getCommandParameter(command,description));
                String taskDesc = Parser.getEndCommandParameter(Integer.toString(taskId),description);
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
                Task todo = Parser.createTodo(userCommand);
                tasks.addTask(todo);
                break;
            case DEADLINE:
                Task deadline =  Parser.createDeadline(userCommand);
                tasks.addTask(deadline);
                break;
            case TIMED:
                Task timed = Parser.createTimed(userCommand);
                tasks.addTask(timed);
                break;
            case PRINT:
                tasks.printTasks();
                break;
            case DONE:
                int doneindex = Parser.getMarkTaskDoneIndex(userCommand);
                tasks.markAsDone(doneindex);
                break;
            case DELETE:
                int deleteindex = Parser.getDeleteTaskIndex(userCommand);
                tasks.deleteTask(deleteindex);
                break;
            case SHOW:
                subcommand = Parser.getCommandWord(Parser.getCommandDescription(userCommand));
                return executeShowCommand(tasks,subcommand,Parser.getCommandDescription(userCommand));
            case CHANGE:
                subcommand = Parser.getCommandWord(Parser.getCommandDescription(userCommand));
                return executeChangeCommand(tasks, subcommand,Parser.getCommandDescription(userCommand));
            default:
                return new Feedback("Unknown Command Entered");
        }
        return new Feedback(isExit);
    }


}
