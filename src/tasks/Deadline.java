package tasks;
import parser.DateTime;

import java.util.Calendar;

public class Deadline extends Todo {

    /**
     * Instance Variable to store the deadline
     */
    private Calendar _deadline;

    /**
     * Constructor to setup deadline task
     * @param description : Task Description
     * @param by : Task Deadline
     */
    public Deadline(String description, Calendar by) {
        super(description);
        this.type = TaskType.DEADLINE;
        _deadline = by;
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
        _deadline = by;
    }

    /**
     * getter for deadline
     * @return jectdeadline as a Calendar ob
     */
    public Calendar getDeadline() {
        return _deadline;
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

}