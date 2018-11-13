import exceptions.TaskManagerException;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;
import parser.DateTime;
import storage.JSONStorage;
import storage.Storage;
import tasklist.TaskList;
import tasks.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class JSONStorageTest {


    static TaskList tasks;
    static Storage storage;
    @Before
    public void testsetup() throws TaskManagerException {
        tasks = new TaskList();
        Task todo = new Todo("xyz1");
        Task dead = new Deadline("xyz2", DateTime.stringToCalendar("23-12-2018,11:14"), TaskStatus.OPEN);
        Task timed = new Timed("xyz3", DateTime.stringToCalendar("23-11-2018,22:11"), DateTime.stringToCalendar("23-11-2018,23:11"),TaskStatus.OPEN);
        tasks.addTask(todo);
        tasks.addTask(dead);
        tasks.addTask(timed);
        storage = new JSONStorage("newjson.json");
        //
    }

    @Test
    public void testWithNull() throws TaskManagerException, FileNotFoundException {
        TaskList nulltasks = new TaskList();
        try {
            storage.writeTasks(nulltasks.getTaskList());
        } catch(Exception e) {
            fail("exception caught while eriting file");
        }
        assertEquals(nulltasks.getTaskList().size(),storage.loadTasks().size());
        assertEquals(0,storage.loadTasks().size());
        assertEquals(0,storage.loadTasks().size());
    }

    @Test
    public void testStorageQuality() throws TaskManagerException, FileNotFoundException, IOException {
        testsetup();

        storage.writeTasks(tasks.getTaskList());

        TaskList checkers = new TaskList(storage.loadTasks());

        if (checkStringEquality(checkers.getTaskList(), tasks.getTaskList())==false){
            fail("Value corrupted when reading from storage");
        }

    }

    private boolean checkStringEquality(List<Task> tasklist1, List<Task> tasklist2) throws IOException {
        if(tasklist1.size()!=tasklist2.size()){
            return false;
        } else {
            for(int i=0; i<tasklist1.size(); i++){
                if(!tasklist1.get(i).toString().equals(tasklist2.get(i).toString())){
                    System.out.println(tasklist1.get(i).toString());
                    System.out.println(tasklist2.get(i).toString());
                    return false;
                }
            }
        }
        return true;
    }


}
