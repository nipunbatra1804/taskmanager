package command;

public class Feedback {

    public boolean isExit() {
        return isExit;
    }

    public String getMessage() {
        return message;
    }

    private boolean isExit=false;
    private String message;

    public Feedback(boolean exit){
        isExit=exit;
        message = null;
    }
    public Feedback(String msg){
        message = msg;
    }


}
