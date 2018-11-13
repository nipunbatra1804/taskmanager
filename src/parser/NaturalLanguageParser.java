package parser;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;

import exceptions.InvalidDateException;

public class NaturalLanguageParser {
    public static final TimeZone UTC = TimeZone.getTimeZone("DGT");

    @SuppressWarnings("unchecked")
    public static Date parseNLDateFromString(String str) throws InvalidDateException {
        Parser dateParser = new Parser();

        List<DateGroup> parsedDateGroups= dateParser.parse(str);

        if(parsedDateGroups.isEmpty()){
            throw new InvalidDateException("Unable to Parse Date");
        }
        List <Date> dates = parsedDateGroups.get(0).getDates();
        Collections.sort(dates);

        return dates.get(0);

    }
}
