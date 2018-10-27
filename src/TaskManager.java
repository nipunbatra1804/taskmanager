import exceptions.TaskManagerException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;
import tasks.Task;
import ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Main Class
 * sets up objects.
 * executes commands
 */
public class TaskManager {

    /**
     * Command Type Keywords:
     */
    private static final String EXIT = "exit";
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String PRINT = "print";
    private static final String DONE = "done";
    private static final String DELETE = "delete";
    private static final String COMPLETED = "completed";
    private static final String INCOMPLETE = "incomplete";
    /**
     * Instance variables/objects
     */
    static TaskList tasks;
    static Ui ui;
    static Storage storage;



    /**
     * Default Constructor to use a new filepath for storage
     * @param filePath specifies the file to be used for storage
     */
    public TaskManager(String filePath) {
        storage = new Storage(filePath);
        setup();
    }

    /**
     * Default Constructor to read from default file
     */
    public TaskManager() {
        storage = new Storage();
        setup();
    }

    /**
     * Function to create new TaskList and UI objects
     */
    private void setup() {
        ui = new Ui();
        try {
            List<Task> tasklist = storage.loadTasks();
            tasks = new TaskList(tasklist);
        } catch (FileNotFoundException e) {
            ui.showToUser("Problem reading file. Starting with an empty task list");
            tasks = new TaskList();
        } catch (TaskManagerException e){
            ui.printError(e.getMessage());
        }
    }

    /**
     * Function get User input from UI object and execute command until User Chooses to exit
     */
    private void runInterface() {
        ui.showWelcomeMessage();
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Your command?");
            try {
                String userCommand = ui.getInput().trim();
                String command = Parser.getCommandWord(userCommand);//extract the first word of the user input
                isExit = executeCommand(isExit, command, userCommand);
            } catch (TaskManagerException e){
                ui.printError(e.getMessage());
            } catch (NumberFormatException e){
                ui.printError(e.getMessage() + "Incorrect Command, Expected number");
            }
        }
        exit();
    }

    /**
     *
     * @param isExit
     * @param command word to specify what command user wants the Task Manager should execute
     * @param  userCommand specifies the usercommand used by parser class
     * @return iExit falg which states user wants to exit
     * @throws TaskManagerException with feedback for user
     */
    private boolean executeCommand(boolean isExit, String command, String userCommand) throws TaskManagerException {
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
            case PRINT:
                tasks.printTasks();
                break;
            case DONE:
                int doneindex = Parser.getMarkTaskDoneIndex(userCommand);
                tasks.markAsDone(doneindex);
                break;
            case DELETE:
                int deleteindex = Parser.getDeleteTaskDoneIndex(userCommand);
                tasks.deleteTask(deleteindex);
                break;
            case COMPLETED:
                List<Task> completed = tasks.getCompleted();
                tasks.printTasks(completed);
                break;
            case INCOMPLETE:
                List<Task> incomplete = tasks.getIncomplete();
                tasks.printTasks(incomplete);
                break;
            default:
                ui.printError("Unexpected Command Occurred");
                break;
        }
        return isExit;
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.runInterface();
    }

    /**
     * Called on exit Writes current tasks in tasklist to storage
     */
    private static void exit() {
        System.out.println("recording data");
        try {
            storage.write(tasks.getTaskList());
        } catch (IOException e){
            ui.printError(e.getMessage());
        }

        System.out.println("Bye!");
    }
}
