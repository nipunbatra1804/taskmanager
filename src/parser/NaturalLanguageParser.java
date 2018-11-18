package parser;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import exceptions.InvalidDateException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Static class for interpreting date as a natural language
 */
public class NaturalLanguageParser {


    /**
     * take in input string and use nattparser library to return a valid date
     */
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
