package tasks;

import exceptions.InvalidCommandParameterException;

/**
 * Enum to indicate task priority
 */
public enum TaskPriority {
    HIGH,MEDIUM,LOW,NONE;

    /**
     * delim used in command to indicate priority
     */
    private static final String DELIM_PRIORITY = "/with";

    /**
     * get priority from command
     * @param command
     * @return priority value from command
     */
    public static TaskPriority getPriorityFrmCmd(String command){
        if(!command.contains(DELIM_PRIORITY)) {
            return TaskPriority.NONE;
        }
        String priority = command.substring(command.indexOf(DELIM_PRIORITY)+DELIM_PRIORITY.length()).trim();
        return TaskPriority.valueOf(priority.toUpperCase());
    }

    /**
     * get priority from string
     * @param description string containing priority
     * @return TaskPriority
     * @throws InvalidCommandParameterException in case string doesnt match priority string.
     */
    public static TaskPriority getPriorityFrmDesc(String description) throws InvalidCommandParameterException {
        String descUpper = description.toUpperCase().trim();
        try {
            return TaskPriority.valueOf(descUpper);
        } catch (IllegalArgumentException ex) {
             throw new InvalidCommandParameterException("Invalid Priority Parameter");
        }
    }
}
