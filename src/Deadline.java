public class Deadline extends Todo {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by,Boolean completed){
        super(description,completed);
        this.by = by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    public String toString(){
        String s = super.toString();
        return (s + "\ndo by: " + this.by);
    }

    public String toFileString(){
        return "T" + " | " + String.valueOf(isDone) + " | " + this.description +
                " | " + this.by;
    }

}
