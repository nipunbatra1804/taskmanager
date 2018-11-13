package tasks;


import org.json.simple.JSONObject;

public class Todo extends Task {
    /**
     * Default Constructor for Todo class
     * @param description; description of the task
     */
    public Todo(String description) {
        this(description, TaskStatus.OPEN);
    }

    /**
     * Constructor
     * @param description task description
     * @param status status of task specifying is OPEN or Completed
     */
    public Todo(String description, TaskStatus status) {
        super(description, status);
        this.type = TaskType.TODO;
    }

    /**
     * getter for completed
     * @return true if completed
     */
    public boolean isCompleted() {
        return TaskStatus.COMPLETED.equals(status);
    }



    @Override
    public String toString(){

        if(TaskStatus.COMPLETED.equals(status)){
            return (super.toString() + "\nis done? Yes");
        } else {
            return (super.toString() + "\nis done? No");
        }
    }

    /**
     * @return file string for storing tasks to file
     */
    public String toFileString(){
        return type.name() + " | " + status.name() + " | " + priority.name() + " | "+ this.description;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jobj = new JSONObject();
        jobj.put("type",type.name());
        jobj.put("description",description);
        jobj.put("status",status.name());
        jobj.put("priority",priority.name());

        return jobj;
    }
}
