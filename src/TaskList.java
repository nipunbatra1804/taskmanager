import java.util.ArrayList;
import java.util.List;
import java.lang.NumberFormatException;

class TaskList {

    private List<Task> _tasks;
    private int count;
    public TaskList(){
        _tasks = new ArrayList<Task>();
        count = 0;
    }
    public TaskList(List<Task> tasks){
        _tasks = tasks;
        count = tasks.size();
    }
    public void addTask(Task t){
        _tasks.add(t);
    }
    public void markAsDone(int index) throws NumberFormatException{
        _tasks.get(index-1).setDone(true);
    }
    public void printTasks(){
        for (int i = 0; i <_tasks.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + _tasks.get(i));
        }
    }
    public List<Task> getTaskList(){
        return _tasks;
    }

}
