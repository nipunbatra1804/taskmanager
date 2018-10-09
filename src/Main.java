import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static List<Task> tasks = new ArrayList<>();
    static int count=0;


    public static void main(String[] args) {
        List<Task> loadedTasks = getTasksFromFile();
        //System.out.println(loadedTasks.size());
        //printTasks(loadedTasks,loadedTasks.size());
        count = count+loadedTasks.size();
        tasks.addAll(loadedTasks);
        printWelcome();
        String line;
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Your task?");
            try {
                line = getInput().trim();
                String command = line.split(" ", 2)[0];//extract the first word of the user input
                switch (command) {
                    case "exit":
                    case "": // exit if user input is empty
                        isExit = true;
                        break;
                    case "todo":
                        addTodo(line);
                        count++;
                        break;
                    case "deadline":
                        addDeadline(line);
                        count++;
                        break;
                    case "print":
                        printTasks();
                        break;
                    case "done":
                        markAsDone(line);
                        break;
                    default:
                        printError();
                        break;
                }
            } catch (TaskManagerException e){
                printError(e.getMessage());
            }
        }
        exit();
    }

    private static List<String> getLines(String filename) throws FileNotFoundException{
        File f = new File(filename);
        Scanner s = new Scanner(f);
        List <String> linesFromFile = new ArrayList<>();
        while(s.hasNext()){
            linesFromFile.add(s.nextLine());
        }
        return linesFromFile;
    }

    private static List<Task> getTasksFromFile() {
        List<Task> loadedTasks = new ArrayList<>();
        try {
            List<String> lines = getLines("data.txt");
            for (String line : lines) {
                if (line.trim().isEmpty()) { //ignore empty lines
                    continue;
                }
                try {
                    loadedTasks.add(createTask(line)); //convert the line to a task and add to the list
                    //System.out.println(createTask(line));
                } catch (TaskManagerException e){
                    printError(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            printError("problem encountered while loading data: " + e.getMessage());
        }
        return loadedTasks;
    }

    private static void writeToFile() throws IOException{
        FileWriter fw = new FileWriter("data.txt");
        for (int i = 0; i < count; i++) {
            fw.write(tasks.get(i).toFileString());
            fw.write("\n");
        }
        fw.close();
    }

    private static Task createTask(String line) throws TaskManagerException{
        String[] filedetails = line.split("\\|");
        String command = filedetails[0].trim();
        Task newTask = null;
        switch(command){
            case "T":
                if(filedetails.length == 3) {
                    newTask = new Todo(filedetails[2].trim(), Boolean.parseBoolean(filedetails[1].trim()));
                } else {
                    throw new TaskManagerException("Incorrect format of Todo information");
                }
                break;
            case "D":
                if(filedetails.length == 4) {
                    newTask = new Deadline(filedetails[2].trim(), filedetails[3].trim(), Boolean.parseBoolean(filedetails[1].trim()));
                } else {
                    throw new TaskManagerException("Incorrect format of Deadline details");
                }
                break;
            default:
                System.out.println(command);
                printError("Unknown format");

        }

        return newTask;
    }


    private static void markAsDone(String line){
        int index  = Integer.parseInt(line.substring("done".length()).trim());
        tasks.get(index-1).setDone(true);
        System.out.println("Tasks in the list: "+ tasks.size());
    }

    private static void addTodo(String line) throws TaskManagerException {
        String description = line.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new TaskManagerException("Empty Description for TODO");
        }
        tasks.add(new Todo(description));
    }
    private static void addDeadline(String line) throws TaskManagerException {
        String description = line.split(" ", 2)[1];
        if(description.indexOf("by")==-1){
            throw new TaskManagerException("Incorrect Format for Deadline");
        }
        String task_description = description.substring(0,description.indexOf("by")).trim();
        String deadline_description = description.substring(description.indexOf("by")+"by".length()).trim();
        if(task_description.isEmpty()){
            throw new TaskManagerException("Empty Description for Deadline");
        }
        if(deadline_description.isEmpty()){
            throw new TaskManagerException("Empty Details for Deadline");
        }

        tasks.add(new Deadline(task_description, deadline_description));

    }

    private static void printError(String e){
        System.out.println(e);
    }
    private static void printError(){
        System.out.println("Unknown command! please try again");
    }
    private static String getInput(){
        return in.nextLine();
    }


    private static void printWelcome() {
        System.out.println("Welcome to TaskManager-Level1!");
    }

    private static void printTasks() {
        for (int i = 0; i < count; i++) {
            System.out.println("[" + (i + 1) + "] " + tasks.get(i));
        }
    }

    private static void printTasks(List<Task> tasklist, int n) {

        for (int i = 0; i <n; i++) {
            System.out.println("[" + (i + 1) + "] " + tasklist.get(i));
        }
    }

    private static void exit() {
        System.out.println("recording data");
        try {
            writeToFile();
        } catch (IOException e){
            printError(e.getMessage());
        }
        System.out.println("Bye!");
    }
}
