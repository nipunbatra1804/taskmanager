public class Todo extends Task {
    public Todo(String description) {
        super(description);
        isDone = false;
    }
    public Todo(String description, Boolean isDone) {
        super(description,isDone);
    }

    public boolean isCompleted() {
        return isDone;
    }

    public String toString(){

        if(isDone){
            return (super.toString() + "\nis done? Yes");
        } else {
            return (super.toString() + "\nis done? No");
        }
    }

    public String toFileString(){
        return "T" + " | " + String.valueOf(isDone) + " | " + this.description;
    }
}
