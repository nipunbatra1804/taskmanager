package storage;

import parser.DateTime;
import tasks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import exceptions.TaskManagerException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONStorage {
    public static final String DEADLINE = TaskType.DEADLINE.name();
    public static final String TODO = TaskType.TODO.name();
    private static String _filename = "data.json";
    public JSONStorage(){
    }

    public JSONStorage(String filename){
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
                } catch (TaskManagerException e){
                    throw e;
                }
            }
        } catch (FileNotFoundException e) {
            throw new TaskManagerException("problem encountered while loading data: " + e.getMessage());
        }
        return loadedTasks;
    }

    private static Task createTask(String line) throws TaskManagerException {
        String[] filedetails = line.split("\\|");
        String command = filedetails[0].trim();
        TaskType type;
        try {
            type = TaskType.valueOf(command);
            String by = filedetails.length > 3 ? filedetails[3].trim() : null;
            return TaskFactory.getTask(type, filedetails[2].trim(), TaskStatus.valueOf(filedetails[1].trim()), by);
        }
        catch (Exception e) {
            throw new TaskManagerException("Invalid Format in Storage");
        }
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
