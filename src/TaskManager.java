import command.Command;
import command.Feedback;
import exceptions.InvalidCommandFormatException;
import exceptions.TaskManagerException;
import parser.Interpreter;
import storage.JSONStorage;
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


    private static TaskList tasks;
    private static Ui ui;
    private static Storage storage;



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
    private TaskManager() {
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
                String command = Interpreter.getCommandWord(userCommand);//extract the first word of the user input
                feedback = Command.executeCommand(tasks,isExit, command, userCommand);
                String message = feedback.getMessage();
                if (message != null){
                    ui.showToUser(message);
                }
            } catch (InvalidCommandFormatException ex) {
                ui.printError("Invalid Command Format entered : " + ex.getMessage());
            } catch (TaskManagerException e){
                ui.printError(e.getMessage());
            } catch (NumberFormatException e){
                ui.printError("Incorrect Command, Expected number");
            }
            isExit = feedback.isExit();
        }
        exit();
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager("newdata.json");
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
