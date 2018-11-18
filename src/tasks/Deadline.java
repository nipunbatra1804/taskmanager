package tasks;
import org.json.simple.JSONObject;
import parser.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Sublass to encapsulate Deadline task
 */
public class Deadline extends Todo {

    /**
     * Instance Variable to store the deadline
     */
    private Calendar deadline;

    /**
     * Constructor to setup deadline task
     * @param description : Task Description
     * @param by : Task Deadline
     */
    public Deadline(String description, Calendar by) {
        super(description);
        this.type = TaskType.DEADLINE;
        deadline = by;
    }

    /**
     * Constructor to setup deadline task
     * @param description : Task Description
     * @param by : Task deadline
     * @param status : Task Status
     */
    public Deadline(String description, Calendar by, TaskStatus status){
        this(description, by);
        this.status = status;
    }

    /**
     * Setter for deadline
     * @param by: deadline
     */
    public void setDeadline(Calendar by) {
        deadline = by;
    }

    /**
     * getter for deadline
     * @return jectdeadline as a Calendar ob
     */
    public Calendar getDeadline() {
        return deadline;
    }

    /**
     * return task description as a string
     */
    @Override
    public String toString(){
        String s = super.toString();
        return (s + "\ndo by: " + DateTime.calendarToString(this.getDeadline()));
    }
    /**
     * @return file string for storing tasks to file
     */
    @Override
    public String toFileString(){
        return type.name() + " | " + status.name() + " | " + this.description +
                " | " + DateTime.calendarToString(this.getDeadline());
    }

    /**
     * return due date or deadline
     * @return deadline
     */
    @Override
    public Date getDueDate(){
        return deadline.getTime();
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
        jobj.put("deadline",DateTime.calendarToString(this.getDeadline()));
        jobj.put("priority",priority.name());
        return jobj;
    }

    /**
     * set due date for a task
     * @param due deadline
     */
    @Override
    public void setDueDate(Calendar due){
        deadline = due;
    }

}
