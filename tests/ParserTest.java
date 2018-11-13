import exceptions.TaskManagerException;
import org.junit.Test;
import parser.DateTime;
import parser.Parser;
import tasks.Deadline;
import tasks.Task;
import tasks.Timed;
import tasks.Todo;

import java.util.Calendar;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class ParserTest {

    @Test
    public void getCommandWord(){
        assertEquals("todo", Parser.getCommandWord("todo read book"));
        assertEquals("deadline", Parser.getCommandWord("deadline return book /by next Friday"));
        assertEquals("exit", Parser.getCommandWord("exit"));
        assertEquals("xyz", Parser.getCommandWord("   xyz   ")); // leading and trailing spaces
        // ...
    }

    @Test
    public void testcreateTodo() throws TaskManagerException {
        Task actual = Parser.createTodo("todo read book");
        Todo expected = new Todo("read book");
        assertEquals(expected.toFileString(), actual.toFileString());
        actual = Parser.createTodo("todo read bok /with low");
        expected = new Todo("read bok");
        assertNotSame(expected.toFileString(), actual.toFileString());
        //...
    }
    @Test
    public void testcreateDeadline() throws TaskManagerException {
        Task actual = Parser.createDeadline("deadline read book /by 23-12-2018,23:14 ");
        Deadline expected = new Deadline("read book", DateTime.stringToCalendar("23-12-2018,23:14"));
        assertEquals(expected.toString(), actual.toString());
        //...
    }
    @Test
    public void testcreateTimed() throws TaskManagerException {
        Task actual = Parser.createTimed("timed read book /from 23-12-2018,23:14 /to 24-12-2018,00:30");
        Calendar from = DateTime.stringToCalendar("23-12-2018,23:14");
        Calendar until = DateTime.stringToCalendar("24-12-2018,00:30");
        Timed expected = new Timed("read book",from,until);
        assertEquals(expected.toFileString(), actual.toFileString());
        //...
    }

    @Test
    public void testmarkDone() throws TaskManagerException{
        assertEquals(1,Parser.getMarkTaskDoneIndex("done 1"));
        assertEquals(-1,Parser.getMarkTaskDoneIndex("done -1"));
        assertEquals(1,Parser.getMarkTaskDoneIndex("done 1.25"));
    }
}
