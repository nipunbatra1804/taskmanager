package tasks;

import org.json.simple.JSONObject;
import parser.DateTime;

import java.util.Calendar;
import java.util.Date;

public class Timed extends Todo {

    private Calendar begin;
    private Calendar end;

    public Timed(String description,Calendar from, Calendar until, TaskStatus status){
        this(description,from,until);
        this.status = status;
    }
    public Timed(String description,Calendar from, Calendar until){
        super(description);
        this.type = TaskType.TIMED;
        begin = from;
        end = until;
    }

    public Calendar getBegin(){
        return begin;
    }
    public Calendar getEnd(){
        return end;
    }

    public void setBegin(Calendar from){
        this.begin = from;
    }
    public void setEnd(Calendar until){
        this.end = until;
    }

    @Override
    public String toString(){
        String s = super.toString();
        return (s + "\ndo from: " + DateTime.calendarToString(this.getBegin()) + "\nto: " +
                DateTime.calendarToString(this.getEnd()) );
    }

    public String toFileString(){
        return type.name() + " | " + status.name() + " | " + this.description +
                " | " + DateTime.calendarToString(this.getBegin()) + " | " + DateTime.calendarToString(this.getEnd());
    }
    @Override
    public Date getDueDate(){
        return end.getTime();
    }

    @Override
    public JSONObject getJson() {
        JSONObject jobj = new JSONObject();
        jobj.put("type",type.name());
        jobj.put("description",description);
        jobj.put("status",status.name());
        jobj.put("from",DateTime.calendarToString(this.getBegin()));
        jobj.put("to",DateTime.calendarToString(this.getEnd()));
        jobj.put("priority",priority.name());
        return jobj;
    }

    public void setDueDate(Calendar due){
        end = due;
    }
}
