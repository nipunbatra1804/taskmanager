package storage;


import org.json.simple.JSONArray;
import tasks.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.TaskManagerException;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONStorage extends Storage{
    public static final String DEADLINE = TaskType.DEADLINE.name();
    public static final String TODO = TaskType.TODO.name();
    private static String filename = "data.json";
    private static JSONObject jsonstore;
    public JSONStorage(){
        jsonstore = new JSONObject();
    }

    public JSONStorage(String filename){
        JSONStorage.filename = filename;
        jsonstore = new JSONObject();
    }

    private static JSONArray getJSONObjects(String filename) throws IOException,FileNotFoundException,ParseException{
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(filename);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        JSONArray taskarray = (JSONArray)jsonObject.get("tasks");

        return taskarray;
    }

    public List<Task> loadTasks() throws TaskManagerException,FileNotFoundException {
        List<Task> loadedTasks = new ArrayList<>();
        try {
            JSONArray taskarray = getJSONObjects(filename);
            for (Object obj : taskarray) {
                try {
                    loadedTasks.add(parseTask((JSONObject)obj)); //convert the line to a task and add to the list
                } catch (TaskManagerException e){
                    throw e;
                }
            }
        } catch (FileNotFoundException e) {
            throw new TaskManagerException("problem encountered while loading data: " + e.getMessage());
        } catch (IOException e){
            throw new TaskManagerException("Unable to open/close file: " + e.getMessage());
        } catch (ParseException e){
            throw new TaskManagerException("Invlaid format of JSOM" + e.getMessage());
        }
        return loadedTasks;
    }

    private static Task parseTask(JSONObject taskobject) throws TaskManagerException {
        TaskType type;
        String by,to,from,status,priority;
        try {
            type = TaskType.valueOf((String)taskobject.get("type"));
            by = (String)taskobject.get("deadline");
            to = by==null?(String)taskobject.get("to"):by;
            from = (String)taskobject.get("from");
            priority = (String)taskobject.get("priority");
            return TaskFactory.getTask(type,(String) taskobject.get("description"),
                        TaskStatus.valueOf((String)taskobject.get("status")),from,to,TaskPriority.valueOf(priority));
        }
        catch (Exception e) {
            throw new TaskManagerException("Invalid Format in Storage");
        }
    }

    public void writeTasks(List<Task> tasks) throws IOException{
        FileWriter fw = new FileWriter(filename);
        JSONArray taskarray = new JSONArray();
        for (int i = 0; i < tasks.size(); i++) {
            taskarray.add(tasks.get(i).getJson());
        }
        jsonstore.put("tasks",taskarray);
        fw.write(jsonstore.toJSONString());
        fw.close();
    }


}
