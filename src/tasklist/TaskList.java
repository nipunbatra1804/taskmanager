package tasklist;

import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.lang.NumberFormatException;
import java.util.stream.Collectors;

public class TaskList {

    /**
     * Instace variable that stores all tasks during runtime
     */
    private List<Task> _tasks;
    /**
     * Instance variable that counts the number of tasks at run time
     */
    private int count;

    /**
     * Constructor to create an Empty Task List
     */
    public TaskList(){
        _tasks = new ArrayList<Task>();
        count = 0;
    }

    /**
     * Task List to create a Taskist object with a list containing tasks
     * @param tasks list of tasks(usually read from storage)
     */
    public TaskList(List<Task> tasks){
        _tasks = tasks;
        count = tasks.size();
    }

    /**
     * Add a task to TaskList
     * @param t: task to be added
     */
    public void addTask(Task t){
        _tasks.add(t);
        count++;
    }

    /**
     * Set a particular task to be completed
     * @param index : index of task in tasklist to be marked as complete
     * @throws NumberFormatException
     */
    public void markAsDone(int index) throws NumberFormatException{
        _tasks.get(index-1).setStatus(TaskStatus.COMPLETED);
    }

    /**
     * Delete a particular task
     * @param index: index of task to be deleted from task list
     * @throws NumberFormatException
     * @throws IndexOutOfBoundsException
     */
    public void deleteTask(int index) throws NumberFormatException, IndexOutOfBoundsException {
        _tasks.remove(index-1);
        count--;
    }

    /**
     * print all tasks in tasklist
     */
    public void printTasks(){
        for (int i = 0; i <_tasks.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + _tasks.get(i));
        }
    }

    /**
     * Print tasks from a List of Task objects
     * @param tasks
     */
    public void printTasks(List<Task> tasks){
        for (int i = 0; i <tasks.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + tasks.get(i));
        }
    }

    /**
     * getter for _tasks variable
     * @return List of Task objetcs
     */
    public List<Task> getTaskList(){
        return _tasks;
    }

    /**
     * Returns list of Tasks with status as COMPLETED
     * @return list of Task objects
     */
    public List<Task> getCompleted(){
        List<Task> completed = new ArrayList<>();
        for(Task t : _tasks){
            if (t.isCompleted()){
                completed.add(t);
            }
        }
        return completed;
    }

    /**
     * Returns list of Tasks with status as OPEN
     * @return list of Task objects
     */
    public List<Task> getIncomplete(){
        return _tasks.stream().filter(s -> !s.isCompleted()).collect(Collectors.toList());
    }



}
