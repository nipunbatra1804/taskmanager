package tasks;

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
    /** task type keeps information about which type of object is being created with this abstract class
     */
    protected TaskType type;

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

}