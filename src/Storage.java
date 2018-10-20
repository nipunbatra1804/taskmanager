

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Storage {

    private static String _filename = "C:\\ToDo\\storage.txt";
    public Storage(){
    }

    public Storage(String filename){
        _filename = filename;
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
    public static List<Task> loadTasks() throws TaskManagerException,FileNotFoundException {
        List<Task> loadedTasks = new ArrayList<>();
        try {
            List<String> lines = getLines(_filename);
            for (String line : lines) {
                if (line.trim().isEmpty()) { //ignore empty lines
                    continue;
                }
                try {
                    loadedTasks.add(createTask(line)); //convert the line to a task and add to the list
                    //System.out.println(createTask(line));
                } catch (TaskManagerException e){
                    throw e;
                }
            }
        } catch (FileNotFoundException e) {
            throw new TaskManagerException("problem encountered while loading data: " + e.getMessage());
        }
        return loadedTasks;
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
                throw new TaskManagerException("Unknown Format");

        }
        return newTask;
    }

    public static void write(List<Task> tasks) throws IOException{
        FileWriter fw = new FileWriter(_filename);
        for (int i = 0; i < tasks.size(); i++) {
            fw.write(tasks.get(i).toFileString());
            fw.write("\n");
        }
        fw.close();
    }


}