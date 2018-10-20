import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class TaskManager {

    static TaskList tasks;
    static Ui ui;
    static Storage storage;

    public TaskManager(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            ui.showToUser("Problem reading file. Starting with an empty task list");
            tasks = new TaskList();
        }catch (TaskManagerException e){
            ui.printError(e.getMessage());
        }
    }
    public TaskManager() {
        ui = new Ui();
        storage = new Storage();
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            ui.showToUser("Problem reading file. Starting with an empty task list");
            tasks = new TaskList();
        } catch (TaskManagerException e){
            ui.printError(e.getMessage());
        }
    }
    public void run() {
        ui.showWelcomeMessage();

        boolean isExit = false;
        while (!isExit) {
            System.out.print("Your task?");
            try {
                String userCommand = ui.getInput().trim();
                String command = Parser.getCommandWord(userCommand);//extract the first word of the user input
                switch (command) {
                    case "exit":
                    case "": // exit if user input is empty
                        isExit = true;
                        break;
                    case "todo":
                        Task todo = Parser.createTodo(userCommand);
                        tasks.addTask(todo);
                        break;
                    case "deadline":
                        Deadline dead =  Parser.createDeadline(userCommand);
                        tasks.addTask(dead);
                        break;
                    case "print":
                        tasks.printTasks();
                        break;
                    case "done":
                        int taskindex = Parser.getMarkTaskDoneIndex(userCommand);
                        tasks.markAsDone(taskindex);
                        break;
                    default:
                        ui.printError("Unexpected Command ocurred");
                        break;
                }
            } catch (TaskManagerException e){
                ui.printError(e.getMessage());
            } catch (NumberFormatException e){
                ui.printError(e.getMessage() + "Incorrect Command, Expected number");
            }
        }
        exit();
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.run();

    }

    private static void exit() {
        System.out.println("recording data");
        try {
            Storage.write(tasks.getTaskList());
        } catch (IOException e){
            ui.printError(e.getMessage());
        }
        System.out.println("Bye!");
    }
}
