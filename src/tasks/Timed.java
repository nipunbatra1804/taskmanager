package tasks;

import org.json.simple.JSONObject;
import parser.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Class encapsulates the Task of timed type which idicates a task to be done from begine to ens
 */
public class Timed extends Todo {

    /**
     * Stores the begin and end timestamps for the task.
     */
    private Calendar begin;
    private Calendar end;

    /**
     * Constructor tor Timed
     * @param description:task descriptiionf
     * @param from begin
     * @param until end
     * @param status is it open or closed
     */
    public Timed(String description,Calendar from, Calendar until, TaskStatus status){
        this(description,from,until);
        this.status = status;
    }

    /**
     * Constructor for Timed without status declaration
     * @param description: task description
     * @param from begin
     * @param until end
     */
    public Timed(String description,Calendar from, Calendar until){
        super(description);
        this.type = TaskType.TIMED;
        begin = from;
        end = until;
    }

    /**
     * getter for timed attribute begin
     * @return begin date&time as a Calendar object
     */
    public Calendar getBegin(){
        return begin;
    }

    /**
     * getter for attribute end
     * @return end date&time as a Calendar object
     */
    public Calendar getEnd(){
        return end;
    }

    /**
     * setter for Begin attribute
     * @param from begin date as calendar
     */
    public void setBegin(Calendar from){
        this.begin = from;
    }

    /**
     * setter for end attribute
     * @param until end date as calendar
     */
    public void setEnd(Calendar until){
        this.end = until;
    }

    @Override
    /**
     * return task description as a string
     */
    public String toString(){
        String s = super.toString();
        return (s + "\ndo from: " + DateTime.calendarToString(this.getBegin()) + "\nto: " +
                DateTime.calendarToString(this.getEnd()) );
    }

    /**
     * @return file string for storing tasks to file
     */
    @Override
    public String toFileString(){
        return type.name() + " | " + status.name() + " | " + this.description +
                " | " + DateTime.calendarToString(this.getBegin()) + " | " + DateTime.calendarToString(this.getEnd());
    }
    @Override
    public Date getDueDate(){
        return end.getTime();
    }
    /**
     * create a new json object with the attributes of the task.
     * @return JSONObject to return to calling function
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject getJson() {
        JSONObject jobj = new JSONObject();
        jobj.put("type",type.name());
        jobj.put("description",description);
        jobj.put("status",status.name());
        jobj.put("from",DateTime.calendarToString(this.getBegin()));
        jobj.put("to",DateTime.calendarToString(this.getEnd()));
        jobj.put("priority",priority.name());
        return jobj;
    }

    public void setDueDate(Calendar due){
        end = due;
    }
}
