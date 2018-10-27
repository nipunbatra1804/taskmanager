import exceptions.TaskManagerException;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import parser.DateTime;
import storage.Storage;
import tasklist.TaskList;
import tasks.Deadline;
import tasks.Task;
import tasks.TaskStatus;
import tasks.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class StorageTest {


    static TaskList tasks;
    static Storage storage;
    @BeforeClass
    public static void testsetup() throws TaskManagerException {
        tasks = new TaskList();
        Task todo = new Todo("xyz1");
        Task dead = new Deadline("xyz2", DateTime.stringToCalendar("23-12-2018,11:14"), TaskStatus.OPEN);
        tasks.addTask(todo);
        tasks.addTask(dead);
        storage = new Storage("newStorageFile.txt");
        //
    }

    @Test
    public void testWithNull() throws TaskManagerException, FileNotFoundException {
        TaskList nulltasks = new TaskList();
        try {
            storage.write(nulltasks.getTaskList());
        } catch(Exception e) {
            fail("exception caught while eriting file");
        }
        assertEquals(nulltasks.getTaskList().size(),storage.loadTasks().size());
        assertEquals(0,storage.loadTasks().size());
        assertEquals(0,storage.loadTasks().size());
    }

    @Test
    public void testStorageQuality() throws TaskManagerException, FileNotFoundException, IOException {
        storage.write(tasks.getTaskList());

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
