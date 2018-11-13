package tasks;

import exceptions.InvalidCommandParameterException;

public enum TaskPriority {
    HIGH,MEDIUM,LOW,NONE;

    private static final String DELIM_PRIORITY = "/with";

    public static TaskPriority getPriorityFrmCmd(String command){
        if(!command.contains(DELIM_PRIORITY)) {
            return TaskPriority.NONE;
        }
        String priority = command.substring(command.indexOf(DELIM_PRIORITY)+DELIM_PRIORITY.length()).trim();
        return TaskPriority.valueOf(priority.toUpperCase());
    }

    public static TaskPriority getPriorityFrmDesc(String description) throws InvalidCommandParameterException {
        String descUpper = description.toUpperCase().trim();
        try {
            return TaskPriority.valueOf(descUpper);
        } catch (IllegalArgumentException ex) {
             throw new InvalidCommandParameterException("Invalid Priority Parameter");
        }
    }
}
