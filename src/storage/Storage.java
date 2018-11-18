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

/**
 * public interface to define how storage interacts with other objects to load and write tasks
 */

public interface Storage {


    /**
     * create a list of tasks from parsing file
     * @return List of Task objects
     * @throws TaskManagerException
     * @throws FileNotFoundException
     */
    public List<Task> loadTasks() throws TaskManagerException ;


    /**
     * Write Tasks descriptions to storage file
     * @param tasks List of Task objects to write to file
     * @throws IOException
     */
    public void writeTasks(List<Task> tasks) throws IOException ;


}