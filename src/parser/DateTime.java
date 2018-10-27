package parser;
import exceptions.InvalidDateException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;

public class DateTime {
    /**
     * Fixed date format for marking time
     */
    private static final String DATE_FORMAT = "dd-mm-yyyy,HH:mm";

    /**
     * convert string to Calendar objects
     * @param datestring :string represneting date
     * @return Calendare object denoting time
     * @throws InvalidDateException thrown if date is of invalid format
     */
    public static Calendar stringToCalendar(String datestring) throws InvalidDateException{
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = sdf.parse(datestring);
            return dateToCalendar(date);
        } catch (ParseException e){
            throw new InvalidDateException("Invalid Date Format. Use\'" + DATE_FORMAT + "\'" + e.getMessage());
        }
    }

    /**
     * returns the Claendar object as a date string
     * @param date input Calendar object
     * @return String of format specified by DATE_FORMAT
     */
    public static String calendarToString(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date.getTime());
    }

    public static String sdftoDate(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(d);
    }
    //Convert Date to Calendar
    private static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    //Convert Calendar to Date
    private static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
