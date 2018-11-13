package storage;

import tasks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import exceptions.TaskManagerException;



public class Storage {


    private static String _filename = "data.txt";

    /**
     * constructor for default filename
     */
    public Storage(){
    }

    /**
     * constructor for filename specified
     * @param filename new filename
     */
    public Storage(String filename){
        _filename = filename;
    }

    /**
     * helper method for getting a list of all lines from file
     * @param filename
     * @return list of Strings
     * @throws FileNotFoundException
     */
    private static List<String> getLines(String filename) throws FileNotFoundException{
        File f = new File(filename);
        Scanner s = new Scanner(f);
        List <String> linesFromFile = new ArrayList<>();
        while(s.hasNext()){
            linesFromFile.add(s.nextLine());
        }
        return linesFromFile;
    }

    /**
     * create a list of tasks from parsing file
     * @return List of Task objects
     * @throws TaskManagerException
     * @throws FileNotFoundException
     */
    public List<Task> loadTasks() throws TaskManagerException,FileNotFoundException {
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


    private Task createTask(String line) throws TaskManagerException {
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

    /**
     * Write Tasks descriptions to storage file
     * @param tasks List of Task objects to write to file
     * @throws IOException
     */
    public void writeTasks(List<Task> tasks) throws IOException{
        FileWriter fw = new FileWriter(_filename);
        for (Task task : tasks) {
            fw.write(task.toFileString());
            fw.write("\n");
        }
        fw.close();
    }


}