package tasks;

import org.json.simple.JSONObject;
import parser.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Abstract class for Todo and Deadline Classes
 */
public abstract class Task {
    /** task description
     */
    protected String description;
    /** task status viz Open, completed etc
     */
    protected TaskStatus status;

    public TaskType getType() {
        return type;
    }

    /** task type keeps information about which type of object is being created with this abstract class
     */
    protected TaskType type;


    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    protected TaskPriority priority = TaskPriority.NONE;

    /**
     *
     * @param description
     */
    public Task(String description) {
        this.description = description;
        status = TaskStatus.OPEN;
    }

    /**
     * Constructor for Task with status specified
      * @param description
     * @param status
     */
    public Task(String description, TaskStatus status){
        this.description = description;
        this.status = status;
    }

    /**
     * Set the status of a particular task
     * @param status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
         this.description = description;
    }
    @Override
    public String toString(){
        return "description: "+ this.description;
    }
    public abstract boolean isCompleted();

    /**
     * return filestring for writing to storage
     * @return
     */
    public abstract String toFileString();
    public abstract JSONObject getJson();
    public Date getDueDate(){
        return null;
    }
    public void setDueDate(Calendar due){
        //Dd Nothing
    }


}