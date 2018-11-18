package parser;
import exceptions.InvalidDateException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

public class DateTime {
    /**
     * Fixed date format for marking time
     */
    private static final String DATE_FORMAT = "dd-MM-yyyy,HH:mm";

    /**
     * convert string to Calendar objects
     * @param datestring :string represneting date
     * @return Calendare object denoting time
     * @throws InvalidDateException thrown if date is of invalid format
     */
    public static Calendar stringToCalendar(String datestring) throws InvalidDateException{
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        boolean isValidDate = isValidFormat(DATE_FORMAT,datestring);
        Date date;
        if(isValidDate){
            try {
                date  = sdf.parse(datestring);
            } catch (ParseException e) {
                throw new InvalidDateException("Invalid Date Format");
            }

        } else {
            date = NaturalLanguageParser.parseNLDateFromString(datestring);
        }

        return dateToCalendar(date);

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

    /**
     * return String of format SDF from input date D
     * @param d
     * @return  date string
     */
    public static String sdftoDate(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(d);
    }
    private static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * returns the current date
     */
    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Compares two dates to return sooner, later
     * @param date1 input param date
     * @param date2 input param date
     * @return 0 if equal, -1 id date1 < date 2, date2 < date 1,
     */
    public static int compareDates(Date date1, Date date2) {
        Date sDate = getZeroTimeDate(date1);
        Date eDate = getZeroTimeDate(date2);
        if (sDate.before(eDate)) {
            return -1;
        }
        if (sDate.after(eDate)) {
            return 1;
        }

        return 0;
    }
    private static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    private static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            //ex.printStackTrace();
        }
        return date != null;
    }

}
