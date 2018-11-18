import exceptions.TaskManagerException;
import org.junit.Test;
import parser.DateTime;
import parser.Interpreter;
import tasks.Deadline;
import tasks.Task;
import tasks.Timed;
import tasks.Todo;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class InterpreterTest {

    @Test
    public void getCommandWord(){
        assertEquals("todo", Interpreter.getCommandWord("todo read book"));
        assertEquals("deadline", Interpreter.getCommandWord("deadline return book /by next Friday"));
        assertEquals("exit", Interpreter.getCommandWord("exit"));
        assertEquals("xyz", Interpreter.getCommandWord("   xyz   ")); // leading and trailing spaces
        // ...
    }

    @Test
    public void testcreateTodo() throws TaskManagerException {
        Task actual = Interpreter.createTodo("todo read book");
        Todo expected = new Todo("read book");
        assertEquals(expected.toFileString(), actual.toFileString());
        actual = Interpreter.createTodo("todo read bok /with low");
        expected = new Todo("read bok");
        assertNotSame(expected.toFileString(), actual.toFileString());
        //...
    }
    @Test
    public void testcreateDeadline() throws TaskManagerException {
        Task actual = Interpreter.createDeadline("deadline read book /by 23-12-2018,23:14 ");
        Deadline expected = new Deadline("read book", DateTime.stringToCalendar("23-12-2018,23:14"));
        assertEquals(expected.toString(), actual.toString());
        //...
    }
    @Test
    public void testcreateTimed() throws TaskManagerException {
        Task actual = Interpreter.createTimed("timed read book /from 23-12-2018,23:14 /to 24-12-2018,00:30");
        Calendar from = DateTime.stringToCalendar("23-12-2018,23:14");
        Calendar until = DateTime.stringToCalendar("24-12-2018,00:30");
        Timed expected = new Timed("read book",from,until);
        assertEquals(expected.toFileString(), actual.toFileString());
        //...
    }

    @Test
    public void testmarkDone() throws TaskManagerException{
        assertEquals(1, Interpreter.getMarkTaskDoneIndex("done 1"));
        assertEquals(-1, Interpreter.getMarkTaskDoneIndex("done -1"));
        assertEquals(1, Interpreter.getMarkTaskDoneIndex("done 1.25"));
    }
}
