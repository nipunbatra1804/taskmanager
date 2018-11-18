package tasklist;

import exceptions.InvalidCommandParameterException;
import parser.DateTime;
import tasks.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.lang.NumberFormatException;
import java.util.stream.Collectors;
import java.lang.IndexOutOfBoundsException;

public class TaskList {

    /**
     * Instace variable that stores all tasks during runtime
     */
    private List<Task> tasks;
    /**
     * Instance variable that counts the number of tasks at run time
     */
    private int count;

    /**
     * Constructor to create an Empty Task List
     */
    public TaskList(){
        tasks = new ArrayList<Task>();
        count = 0;
    }

    /**
     * Task List to create a Taskist object with a list containing tasks
     * @param tasks list of tasks(usually read from storage)
     */
    public TaskList(List<Task> tasks){
        this.tasks = tasks;
        count = tasks.size();
    }

    /**
     * Add a task to TaskList
     * @param t: task to be added
     */
    public void addTask(Task t){
        tasks.add(t);
        count++;
    }

    /**
     * Set a particular task to be completed
     * @param index : index of task in tasklist to be marked as complete
     * @throws NumberFormatException
     */
    public void markAsDone(int index) throws NumberFormatException, IndexOutOfBoundsException{
        tasks.get(index-1).setStatus(TaskStatus.COMPLETED);
    }

    /**
     * Delete a particular task
     * @param index: index of task to be deleted from task list
     * @throws NumberFormatException
     * @throws IndexOutOfBoundsException
     */
    public void deleteTask(int index) throws NumberFormatException, IndexOutOfBoundsException {
        tasks.remove(index-1);
        count--;
    }

    /**
     * print all tasks in tasklist
     */
    public void printTasks(){
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + tasks.get(i));
        }
    }

    /**
     * Print tasks from a List of Task objects
     * @param listTasks list of tasks to print out.
     */
    public String printTasks(List<Task> listTasks){
        String listOfTasks = "";
        for (int i = 0; i <listTasks.size(); i++) {
            listOfTasks = listOfTasks + "[" + (i + 1) + "] " + listTasks.get(i).toFileString();
        }
        return listOfTasks;
    }

    /**
     * getter for tasks variable
     * @return List of Task objetcs
     */
    public List<Task> getTaskList(){
        return tasks;
    }

    /**
     * Returns list of Tasks with status as COMPLETED
     * @return list of Task objects
     */
    public List<Task> getCompleted(){
        List<Task> completed = new ArrayList<>();
        for(Task t : tasks){
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
        return tasks.stream().filter(s -> !s.isCompleted()).collect(Collectors.toList());
    }


    /**
     * returns a list of tasks of a particular priority
     * @param priority: desired priority
     * @return List<Task> object of priority
     * @throws InvalidCommandParameterException if an invalid priority is passed as an argument
     */
    public List<Task> getPriority(String priority) throws InvalidCommandParameterException {
        TaskPriority taskPriority ;
        try {
            taskPriority = TaskPriority.valueOf(priority.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new InvalidCommandParameterException("Invalid Priority Parameter");
        }
        return tasks.stream().filter(s -> s.getPriority()==taskPriority).collect(Collectors.toList());
    }

    /**
     * returns a list of tasks that are due today
     * @return list of tasks due today
     */
    public List<Task> getDueToday() {
        Date today = DateTime.getToday();
        List<Task> datedtasks = tasks.stream().filter(s -> s.getType() == TaskType.DEADLINE  || s.getType()== TaskType.TIMED).collect(Collectors.toList());
        return datedtasks.stream().filter(s -> DateTime.compareDates(today,s.getDueDate())==0).collect(Collectors.toList());
    }

    /** change priotrity of a task
     *
     * @param index of task to be modified
     * @param priority of task to be modified to
     * @throws IndexOutOfBoundsException thrown if an invalid task index is given
     */
    public void changePriority(int index, TaskPriority priority) throws IndexOutOfBoundsException{
        tasks.get(index-1).setPriority(priority);
    }

    /**
     * change date of a task
     * @param index of task to be modified
     * @param due date of task be modified to
     * @throws IndexOutOfBoundsException if task doesnt exist
     */
    public void changeDate(int index, Calendar due) throws IndexOutOfBoundsException{
        tasks.get(index-1).setDueDate(due);
    }

    /**
     * change description of a task.
     * @param index of a task to be modified
     * @param taskDesc new description
     * @throws IndexOutOfBoundsException if task doesnt exist
     */
    public void changeDesc(int index, String taskDesc) throws IndexOutOfBoundsException {
        tasks.get(index-1).setDescription(taskDesc);
    }
}
