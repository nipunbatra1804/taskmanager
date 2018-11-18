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

public class JSONStorage implements Storage{

    private final String DEAFULT_FILENAME  = "data.json";

    /**
     * filename containing the address of the JSON object
     */
    private String filename = DEAFULT_FILENAME;

    /**
     * JSONObject attribute used for parsing and constructiong json strings.
     */
    private JSONObject jsonstore;

    /**
     * Constructor to read/write from default flile
     */
    public JSONStorage(){
        jsonstore = new JSONObject();
    }

    /**
     * Constructor to specify new filename to read and write from.
     * @param filename:filename
     */
    public JSONStorage(String filename){
        this.filename = filename;
        jsonstore = new JSONObject();
    }

    /**
     * gets a list of  Json objects of type JSONArray.
     * @param filename file name of json file
     * @return
     * @throws IOException in case file is not readable
     * @throws FileNotFoundException in case file doesnt exist
     * @throws ParseException in case json file's structure is corrupted
     */
    private static JSONArray getJSONObjects(String filename) throws IOException,FileNotFoundException,ParseException{
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(filename);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        JSONArray taskarray = (JSONArray)jsonObject.get("tasks");

        return taskarray;
    }

    /**
     * returns a list of tasks that were read from the specified json file
     * @return list of tsks in storage
     * @throws TaskManagerException in case loading of tasks fails.
     */
    public List<Task> loadTasks() throws TaskManagerException {
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

    /**
     * parses a json object to return a Task object
     * @param taskobject JSON object extracted from file storage
     * @return Task object
     * @throws TaskManagerException if Task in storage of invalid format
     */
    private static Task parseTask(JSONObject taskobject) throws TaskManagerException {
        TaskType type;
        String by,to,from,status,priority;
        try {
            type = TaskType.valueOf((String)taskobject.get("type"));
            by = (String)taskobject.get("deadline");
            to = by==null?(String)taskobject.get("to"):by;
            from = (String)taskobject.get("from");
            priority = (String)taskobject.get("priority");
            status = (String)taskobject.get("status");
            return TaskFactory.getTask(type,(String) taskobject.get("description"),
                        TaskStatus.valueOf(status),from,to,TaskPriority.valueOf(priority));
        }
        catch (Exception e) {
            throw new TaskManagerException("Invalid Format in Storage");
        }
    }

    /**
     * Write Tasks descriptions to storage file
     * @param tasks List of Task objects to write to file
     * @throws IOException : if unable to write file.
     */
    @SuppressWarnings("unchecked")
    public void writeTasks(List<Task> tasks) throws IOException{
        FileWriter fw = new FileWriter(filename);
        JSONArray taskarray = new JSONArray();
        for (Task task : tasks) {
            taskarray.add(task.getJson());
        }
        jsonstore.put("tasks",taskarray);
        fw.write(jsonstore.toJSONString());
        fw.close();
    }


}
