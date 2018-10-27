import exceptions.TaskManagerException;
import org.junit.Test;
import parser.DateTime;
import parser.Parser;
import tasks.Deadline;
import tasks.Task;
import tasks.Todo;

import static junit.framework.TestCase.assertEquals;

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
        assertEquals(expected.toString(), actual.toString());
        //...
    }
    @Test
    public void testcreateDeadline() throws TaskManagerException {
        Task actual = Parser.createDeadline("deadline read book by 23-12-2018,23:14 ");
        Deadline expected = new Deadline("read book", DateTime.stringToCalendar("23-12-2018,23:14"));
        assertEquals(expected.toString(), actual.toString());
        //...
    }

    @Test
    public void testmarkDone(){
        assertEquals(1,Parser.getMarkTaskDoneIndex("done 1"));
        assertEquals(-1,Parser.getMarkTaskDoneIndex("done -1"));
        assertEquals(1,Parser.getMarkTaskDoneIndex("done 1.25"));
    }
}
