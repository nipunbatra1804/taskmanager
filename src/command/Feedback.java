package command;

/**
 * class to define feedbcak between command execution and calling function
 */
public class Feedback {
    /**
     * class attributes
     * isExit = exit from the command loop
     */
    private boolean isExit=false;
    private String message;
    /**
     * getter function for isExit
     * @return isExit
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * getter for feeeback message
     * @return message
     */
    public String getMessage() {
        return message;
    }


    /**
     * constructor for Feedback class setting the exit parameter
     * @param exit ; isExit
     */
    public Feedback(boolean exit){
        isExit=exit;
        message = null;
    }

    /**
     * constructor for Feedback class setting the message parameter
     * @param msg : message
     */
    public Feedback(String msg){
        message = msg;
    }


}
