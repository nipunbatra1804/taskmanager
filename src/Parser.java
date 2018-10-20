public class Parser {

    public static String getCommandWord(String fullcommand){
        String command = fullcommand.split(" ", 2)[0];
        return command;
    }

    public static Todo createTodo(String fullcommand) throws TaskManagerException{
        String description = fullcommand.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new TaskManagerException("Empty Description for TODO");
        }
        return new Todo(description);
    }

    public static Deadline createDeadline(String fullcommand) throws TaskManagerException{
        String description = fullcommand.substring("deadline".length()).trim();
        if(description.indexOf("by")==-1){
            throw new TaskManagerException("Incorrect Format for Deadline");
        }
        String task_description = description.substring(0,description.indexOf("by")).trim();
        String deadline_description = description.substring(description.indexOf("by")+"by".length()).trim();
        if(task_description.isEmpty()){
            throw new TaskManagerException("Empty Description for Deadline");
        }
        if(deadline_description.isEmpty()){
            throw new TaskManagerException("Empty Details for Deadline");
        }
        return new Deadline(task_description, deadline_description);
    }

    public static int getMarkTaskDoneIndex(String line){
        return Integer.parseInt(line.substring("done".length()).trim());
    }

}


    /*
    private static void addTodo(String line) throws TaskManagerException {
        String description = line.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new TaskManagerException("Empty Description for TODO");
        }
        tasks.add(new Todo(description));
    }
    private static void addDeadline(String line) throws TaskManagerException {
        String description = line.split(" ", 2)[1];
        if(description.indexOf("by")==-1){
            throw new TaskManagerException("Incorrect Format for Deadline");
        }
        String task_description = description.substring(0,description.indexOf("by")).trim();
        String deadline_description = description.substring(description.indexOf("by")+"by".length()).trim();
        if(task_description.isEmpty()){
            throw new TaskManagerException("Empty Description for Deadline");
        }
        if(deadline_description.isEmpty()){
            throw new TaskManagerException("Empty Details for Deadline");
        }
        tasks.add(new Deadline(task_description, deadline_description));

    }

    */
