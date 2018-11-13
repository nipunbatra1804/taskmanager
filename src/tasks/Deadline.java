package tasks;
import org.json.simple.JSONObject;
import parser.DateTime;

import java.util.Calendar;
import java.util.Date;

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

    @Override
    public String toString(){
        String s = super.toString();
        return (s + "\ndo by: " + DateTime.calendarToString(this.getDeadline()));
    }

    public String toFileString(){
        return type.name() + " | " + status.name() + " | " + this.description +
                " | " + DateTime.calendarToString(this.getDeadline());
    }

    @Override
    public Date getDueDate(){
        return deadline.getTime();
    }

    @Override
    public JSONObject getJson() {
        JSONObject jobj = new JSONObject();
        jobj.put("type",type.name());
        jobj.put("description",description);
        jobj.put("status",status.name());
        jobj.put("deadline",DateTime.calendarToString(this.getDeadline()));
        jobj.put("priority",priority.name());
        return jobj;
    }

    @Override
    public void setDueDate(Calendar due){
        deadline = due;
    }

}
