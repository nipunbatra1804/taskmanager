package ui;

import java.util.Scanner;

public class Ui {
    public static final String ANSI_RESET = "\033[0m";//"\u001B[0m";
    public static final String ANSI_RED = "\033[0;31m"; //"\u001B[31m";

    private static Scanner _in = new Scanner(System.in);
    public Ui(){
    }
    public void showWelcomeMessage(){
        System.out.println("Welcome to Nipun's Ultimate tasks.Task Manager");
    }
    public void printError(){
        System.out.println("Unexpected Error Occurred! Please try again");
    }

    /**
     * Displays Error to user when handling exceptions
     * @param e input string to display to user
     */
    public static void printError(String e){
        System.out.println(e);
    }

    /**
     *
     * @return value from CLI entered by user
     */
    public static String getInput(){
        return _in.nextLine();
    }

    /**
     * Show information to CLI
     */
    public static void showToUser(String show){
        System.out.println(show);
    }

    public static void promptUser(String show){
        System.out.println(ANSI_RED + show + ANSI_RESET);
    }


}
