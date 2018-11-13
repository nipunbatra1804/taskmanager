
import exceptions.InvalidDateException;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import parser.DateTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;

import exceptions.TaskManagerException;
import org.junit.Test;
import parser.DateTime;
import parser.Parser;
import tasks.Deadline;
import tasks.Task;
import tasks.Todo;


public class DateTimeTest {

    @Test
    public void testStringToCalendar() throws InvalidDateException {
        Calendar c = Calendar.getInstance();
        c.set(2018,11,20,17,45,0);
        Calendar d = DateTime.stringToCalendar("20-12-2018,17:45");
        assertEquals(DateTime.sdftoDate(d.getTime()),DateTime.sdftoDate(c.getTime()));
    }



}
