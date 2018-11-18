package tasks;

import org.json.simple.JSONObject;

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
     * Constructor for Task with status set as OPEN
     * @param description description of task
     */
    public Task(String description) {
        this.description = description;
        status = TaskStatus.OPEN;
    }

    /**
     * Constructor for Task with status specified
      * @param description description of task
     * @param status status to set task to
     */
    public Task(String description, TaskStatus status){
        this.description = description;
        this.status = status;
    }

    /**
     * Set the status of a particular task
     * @param status to set attribute this.status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    /**
     * return task description as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for task description.
     * @param description string to set task description to.
     */
    public void setDescription(String description) {
         this.description = description;
    }
    @Override
    public String toString(){
        return "description: "+ this.description + " of " + priority.toString() + " priority";
    }
    public abstract boolean isCompleted();

    /**
     * return filestring for writing to storage
     * @return string that would be written to a file.
     */
    public abstract String toFileString();

    /**
     * return JSON object for task attributes
     * @return JSON object
     */
    public abstract JSONObject getJson();

    /**
     * get due date for a task
     * @return : Date deadline/end
     */
    public Date getDueDate(){
        return null;
    }

    /**
     * set due date for a tas
     * @param due : Calendar deadline/end
     */
    public void setDueDate(Calendar due){
        //Dd Nothing
    }


}