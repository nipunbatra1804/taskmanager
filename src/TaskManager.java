import command.Command;
import command.Feedback;
import exceptions.InvalidCommandFormatException;
import exceptions.TaskManagerException;
import parser.Parser;
import storage.JSONStorage;
import storage.Storage;
import tasklist.TaskList;
import tasks.Task;
import tasks.TaskPriority;
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


    static TaskList tasks;
    static Ui ui;
    static Storage storage;



    /**
     * Default Constructor to use a new filepath for storage
     * @param filePath specifies the file to be used for storage
     */
    public TaskManager(String filePath) {
        storage = new JSONStorage(filePath);
        setup();
    }

    /**
     * Default Constructor to read from default file
     */
    public TaskManager() {
        storage = new JSONStorage();
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
            tasks = new TaskList();
        }
    }

    /**
     * Function get User input from UI object and execute command until User Chooses to exit
     */
    private void runInterface() {
        ui.showWelcomeMessage();
        boolean isExit = false;
        Feedback feedback = new Feedback(isExit);
        while (!isExit) {
            ui.promptUser("Enter Command");
            try {
                String userCommand = ui.getInput().trim();
                String command = Parser.getCommandWord(userCommand);//extract the first word of the user input
                feedback = Command.executeCommand(tasks,isExit, command, userCommand);
            } catch (InvalidCommandFormatException ex) {
                ui.printError("Invalid Command Format entered");
            } catch (TaskManagerException e){
                ui.printError(e.getMessage());
            } catch (NumberFormatException e){
                ui.printError(e.getMessage() + "Incorrect Command, Expected number");
            }
            isExit = feedback.isExit();
            String message = feedback.getMessage();
            if (message != null){
                ui.showToUser(message);
            }
        }
        exit();
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.runInterface();
    }

    /**
     * Called on exit Writes current tasks in tasklist to storage
     */
    private static void exit() {
        try {
            storage.writeTasks(tasks.getTaskList());
        } catch (IOException e){
            ui.printError(e.getMessage());
        }

        System.out.println("Bye!");
    }
}
