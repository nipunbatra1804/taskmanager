import exceptions.TaskManagerException;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import parser.DateTime;
import storage.Storage;
import tasklist.TaskList;
import tasks.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class TaskFactoryTest {
    @Test
    public void createBasicObjetcs() throws TaskManagerException{
        Task deadline = TaskFactory.getTask(TaskType.DEADLINE,"al",TaskStatus.OPEN,"23-11-2018,17:44");
        Task todo = TaskFactory.getTask(TaskType.TODO,"ks",TaskStatus.OPEN,"23-11-2018,17:44");

        Task deadline1 = new Deadline("al",DateTime.stringToCalendar("23-11-2018,17:44"),TaskStatus.OPEN);//TaskFactory.getTask(TaskType.DEADLINE,"al",TaskStatus.OPEN,"23-11-2018,17:44");
        Task todo1 = new Todo("ks",TaskStatus.OPEN);

        assertEquals(deadline.toString(),deadline1.toString());
        assertEquals(todo.toString(),todo1.toString());

         deadline = TaskFactory.getTask(TaskType.TODO,"al",TaskStatus.OPEN,"23-11-2018,17:44");
         todo = TaskFactory.getTask(TaskType.DEADLINE,"ks",TaskStatus.OPEN,"23-11-2018,17:44");

         deadline1 = new Deadline("al",DateTime.stringToCalendar("23-11-2018,17:44"),TaskStatus.OPEN);//TaskFactory.getTask(TaskType.DEADLINE,"al",TaskStatus.OPEN,"23-11-2018,17:44");
         todo1 = new Todo("ks",TaskStatus.OPEN);

        assertNotEquals(deadline.toString(),deadline1.toString());
        assertNotEquals(todo.toString(),todo1.toString());


    }





}
